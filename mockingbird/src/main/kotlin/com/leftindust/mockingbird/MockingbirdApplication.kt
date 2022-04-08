package com.leftindust.mockingbird


import com.leftindust.mockingbird.external.icd.IcdApiClient
import com.leftindust.mockingbird.graphql.hooks.CustomSchemaGeneratorHooks
import kotlin.annotation.AnnotationRetention.SOURCE
import kotlin.annotation.AnnotationTarget.FUNCTION
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


@SpringBootApplication
@EnableConfigurationProperties(IcdApiClient::class)
class MockingbirdApplication {
    @Bean
    fun hooks() = CustomSchemaGeneratorHooks(emptyList())
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