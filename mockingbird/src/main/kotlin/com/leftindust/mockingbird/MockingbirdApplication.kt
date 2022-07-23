package com.leftindust.mockingbird


import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.leftindust.mockingbird.config.CorsConfiguration
import com.leftindust.mockingbird.config.FirebaseConfiguration
import com.leftindust.mockingbird.config.IcdApiClientConfiguration
import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.graphql.execution.RuntimeWiringConfigurer
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.OAuth2ResourceServerSpec
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.reactive.CorsUtils
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.Base64
import java.util.UUID


@SpringBootApplication
@EnableConfigurationProperties(IcdApiClientConfiguration::class, CorsConfiguration::class, FirebaseConfiguration::class)
class MockingbirdApplication {
    @Bean
    fun runtimeWiringConfigurer(): RuntimeWiringConfigurer = RuntimeWiringConfigurer { builder ->
        builder
            .scalar(
                GraphQLScalarType.newScalar()
                    .name("UUID")
                    .coercing(object : Coercing<UUID, String> {
                        override fun serialize(dataFetcherResult: Any): String {
                            return try {
                                if (dataFetcherResult is UUID) {
                                    dataFetcherResult.toString()
                                } else {
                                    throw CoercingSerializeException("UUID must be a UUID, instead was $dataFetcherResult")
                                }
                            } catch (e: Exception) {
                                throw CoercingSerializeException("failed to coerce $dataFetcherResult to String", e)
                            }
                        }

                        override fun parseValue(input: Any): UUID {
                            if (input is String) {
                                try {
                                    return UUID.fromString(input)
                                } catch (e: Exception) {
                                    throw CoercingSerializeException("failed to coerce $input to UUID", e)
                                }
                            } else {
                                throw CoercingSerializeException("UUID must be a string, instead was $input")
                            }
                        }

                        override fun parseLiteral(input: Any): UUID {
                            if (input is StringValue) {
                                try {
                                    return UUID.fromString(input.value)
                                } catch (e: Exception) {
                                    throw CoercingSerializeException("failed to coerce $input", e)
                                }
                            } else {
                                throw CoercingSerializeException("UUID must be a StringValue, instead was $input")
                            }
                        }
                    })
                    .build()
            )
            .scalar(GraphQLScalarType
                .newScalar()
                .name("Base64")
                .coercing(object : Coercing<ByteArray, String> {
                    override fun serialize(dataFetcherResult: Any): String {
                        try {
                            return if (dataFetcherResult is ByteArray) {
                                Base64.getEncoder().encodeToString(dataFetcherResult)
                            } else {
                                throw CoercingSerializeException("Base64 must be a ByteArray, instead was $dataFetcherResult")
                            }
                        } catch (e: Exception) {
                            throw CoercingSerializeException("failed to coerce $dataFetcherResult to String", e)
                        }
                    }

                    override fun parseValue(input: Any): ByteArray {
                        try {
                            return if (input is String) {
                                Base64.getDecoder().decode(input)
                            } else {
                                throw CoercingSerializeException("Base64 must be a String, instead was $input")
                            }
                        } catch (e: Exception) {
                            throw CoercingSerializeException("failed to coerce $input to ByteArray", e)
                        }
                    }

                    override fun parseLiteral(input: Any): ByteArray {
                        try {
                            return if (input is StringValue) {
                                Base64.getDecoder().decode(input.value)
                            } else {
                                throw CoercingSerializeException("Base64 must be a StringValue, instead was $input")
                            }
                        } catch (e: Exception) {
                            throw CoercingSerializeException("failed to coerce $input", e)
                        }
                    }
                }).build())
    }

    @Bean
    fun firebaseApp(firebaseConfiguration: FirebaseConfiguration): FirebaseApp =
        runCatching { FirebaseApp.getInstance() }.getOrElse {
            val serviceAccountKey = ClassPathResource(firebaseConfiguration.accountKeyPath)
            val googleCredentials = GoogleCredentials.fromStream(serviceAccountKey.inputStream)
            FirebaseApp.initializeApp(
                FirebaseOptions.builder()
                    .setCredentials(googleCredentials)
                    .build()
            )
        }

    @Bean
    fun firebaseAuth(firebaseConfiguration: FirebaseConfiguration): FirebaseAuth {
        firebaseApp(firebaseConfiguration)
        return FirebaseAuth.getInstance()
    }

    @Bean
    fun corsFilter(corsConfiguration: CorsConfiguration) = WebFilter { ctx: ServerWebExchange, chain: WebFilterChain ->
        val request = ctx.request
        if (CorsUtils.isCorsRequest(request)) {
            val response = ctx.response
            with(corsConfiguration) {
                response.headers.apply {
                    add("Access-Control-Allow-Origin", allowedOrigin)
                    add("Access-Control-Allow-Methods", allowedMethods)
                    add("Access-Control-Max-Age", maxAge)
                    add("Access-Control-Allow-Headers", allowedHeaders)
                }
            }
            if (request.method == HttpMethod.OPTIONS) {
                ctx.response.statusCode = HttpStatus.OK
                return@WebFilter Mono.empty<Void>()
            }
        }
        return@WebFilter chain.filter(ctx)
    }

    @Bean
    fun httpSecurity(@Suppress("SpringJavaInjectionPointsAutowiringInspection") http: ServerHttpSecurity): SecurityWebFilterChain =
        http
            .csrf { it.disable() }
            .authorizeExchange { it.anyExchange().permitAll() }
            .oauth2ResourceServer(OAuth2ResourceServerSpec::jwt)
            .build()
}

/**
 * Entry point into the application, starts doing the spring magic
 * @param args command line arguments
 */
fun main(args: Array<String>) {
    runApplication<MockingbirdApplication>(*args)
}