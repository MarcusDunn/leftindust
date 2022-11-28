package com.leftindust.mockingbird


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.leftindust.mockingbird.config.AwsEmailConfiguration
import com.leftindust.mockingbird.config.CorsConfiguration
import com.leftindust.mockingbird.config.FirebaseConfiguration
import com.leftindust.mockingbird.config.IcdApiClientConfiguration
import graphql.schema.GraphQLScalarType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.core.io.ClassPathResource
import org.springframework.graphql.execution.RuntimeWiringConfigurer
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.OAuth2ResourceServerSpec
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.reactive.CorsUtils
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.templatemode.TemplateMode
import reactor.core.publisher.Mono
import java.time.Clock
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


@SpringBootApplication
@EnableConfigurationProperties(IcdApiClientConfiguration::class, CorsConfiguration::class, FirebaseConfiguration::class, AwsEmailConfiguration::class)
class MockingbirdApplication {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Bean("jsonMapper")
    @Primary
    fun mappingJackson2HttpMessageConverter(): ObjectMapper {
        return Jackson2ObjectMapperBuilder().build<ObjectMapper>()
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
    }

    @Bean
    fun clock(): Clock = Clock.systemUTC()

    @Bean
    fun runtimeWiringConfigurer(): RuntimeWiringConfigurer {
        return RuntimeWiringConfigurer { builder ->
            builder
                .scalar(
                    GraphQLScalarType
                        .newScalar()
                        .name("UUID")
                        .coercing(StringCoercing(UUID::fromString, UUID::toString))
                        .build()
                ).scalar(
                    GraphQLScalarType
                        .newScalar()
                        .name("Base64")
                        .coercing(StringCoercing(Base64.getDecoder()::decode, Base64.getEncoder()::encodeToString))
                        .build()
                ).scalar(
                    GraphQLScalarType
                        .newScalar()
                        .name("Duration")
                        .coercing(StringCoercing(Duration::parse, Duration::toString))
                        .build()
                ).scalar(
                    GraphQLScalarType
                        .newScalar()
                        .name("LocalDateTime")
                        .coercing(StringCoercing(LocalDateTime::parse, LocalDateTime::toString))
                        .build()
                ).scalar(
                    GraphQLScalarType
                        .newScalar()
                        .name("LocalDate")
                        .coercing(StringCoercing(LocalDate::parse, LocalDate::toString))
                        .build()
                )
        }
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
    fun httpSecurity(http: ServerHttpSecurity): SecurityWebFilterChain =
        http
            .csrf { it.disable() }
            .authorizeExchange { it.anyExchange().permitAll() }
            .oauth2ResourceServer(OAuth2ResourceServerSpec::jwt)
            .build()

    @Bean
    fun templateResolver(): SpringResourceTemplateResolver {
        val templateResolver = SpringResourceTemplateResolver()
        templateResolver.setApplicationContext(applicationContext)
        templateResolver.prefix = "classpath:/templates/"
        templateResolver.suffix = ".html"
        // HTML is the default value, added here for the sake of clarity.
        templateResolver.templateMode = TemplateMode.HTML
        return templateResolver
    }

    @Bean
    fun springTemplateEngine(): SpringTemplateEngine {
        val templateEngine = SpringTemplateEngine()
        templateEngine.setTemplateResolver(templateResolver())
        templateEngine.enableSpringELCompiler = true
        return templateEngine
    }

    @Bean
    fun javaMailSender(): JavaMailSender = JavaMailSenderImpl()
}

/**
 * Entry point into the application, starts doing the spring magic
 * @param args command line arguments
 */
fun main(args: Array<String>) {
    runApplication<MockingbirdApplication>(*args)
}