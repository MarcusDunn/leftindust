package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.util.debugPrint
import com.leftindust.mockingbird.util.gqlRequest
import com.leftindust.mockingbird.util.integration.NoAuthIntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.test.web.reactive.server.WebTestClient

@AutoConfigureWebTestClient
class IcdQueryIntegrationTest(
    @Autowired private val webTestClient: WebTestClient
) : NoAuthIntegrationTest() {
    @Test
    internal fun `test search for aids`() {
        webTestClient.gqlRequest(
            //language=graphql
            """
            query {
                searchIcd(query: "aids") {
                    error
                }
            }
        """.trimIndent()
        )
            .debugPrint()
            .expectBody()
            .jsonPath("data.searchIcd.error")
            .isEqualTo(false)
    }
}