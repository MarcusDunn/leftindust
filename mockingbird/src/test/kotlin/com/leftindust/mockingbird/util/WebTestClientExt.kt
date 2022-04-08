package com.leftindust.mockingbird.util

import org.springframework.test.web.reactive.server.WebTestClient

fun WebTestClient.gqlRequest(request: String): WebTestClient.ResponseSpec = this.post()
    .uri(GRAPHQL_ENDPOINT)
    .accept(APPLICATION_JSON_MEDIA_TYPE)
    .contentType(GRAPHQL_MEDIA_TYPE)
    .bodyValue(request)
    .exchange()