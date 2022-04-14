package com.leftindust.mockingbird.graphql

import com.leftindust.mockingbird.util.integration.IntegrationTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalToCompressingWhiteSpace
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
        assertThat(
            request.responseBody!!.decodeToString(),
            equalToCompressingWhiteSpace(schemaFile.readText())
        )
    }
}