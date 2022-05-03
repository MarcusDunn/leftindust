package com.leftindust.mockingbird


import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.leftindust.mockingbird.config.CorsConfiguration
import com.leftindust.mockingbird.config.FirebaseConfiguration
import com.leftindust.mockingbird.config.IcdApiClientConfiguration
import com.leftindust.mockingbird.graphql.CustomSchemaGeneratorHooks
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.web.server.ServerHttpSecurity.OAuth2ResourceServerSpec
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.web.cors.reactive.CorsUtils
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import kotlin.annotation.AnnotationRetention.SOURCE
import kotlin.annotation.AnnotationTarget.FUNCTION


@SpringBootApplication
@EnableConfigurationProperties(IcdApiClientConfiguration::class, CorsConfiguration::class, FirebaseConfiguration::class)
class MockingbirdApplication {
    @Bean
    fun hooks() = CustomSchemaGeneratorHooks(emptyList())

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
    fun httpSecurity(http: ServerHttpSecurity): SecurityWebFilterChain = http
        .csrf { it.disable() }
        .authorizeExchange { it.anyExchange().authenticated() }
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

@Target(FUNCTION)
@Retention(SOURCE)
annotation class Blocking