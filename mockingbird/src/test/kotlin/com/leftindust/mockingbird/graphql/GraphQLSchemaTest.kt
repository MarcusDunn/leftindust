package com.leftindust.mockingbird.graphql

import com.leftindust.mockingbird.util.integration.IntegrationTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.core.io.ClassPathResource
import org.springframework.test.web.reactive.server.WebTestClient

@AutoConfigureWebTestClient
class GraphQLSchemaTest(
    @Autowired private val webTestClient: WebTestClient
) : IntegrationTest() {

    @Test
    internal fun `check schema is up to date`() {
        val request = webTestClient.get()
            .uri("/sdl")
            .exchange()
            .expectBody()
            .returnResult()
        val schemaFile = ClassPathResource("schema.graphqls").file
        assertEquals(
            request.responseBody!!.decodeToString().filterNot { it.isWhitespace() },
            schemaFile.readText().filterNot { it.isWhitespace() }
        )
    }
}