package com.leftindust.mockingbird.util

import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import java.util.*

fun WebTestClient.ResponseSpec.verifyOnlyDataExists(expectedQuery: String): WebTestClient.BodyContentSpec {
    return runCatching {
        this.expectBody()
            .jsonPath("$DATA_JSON_PATH.$expectedQuery").exists()
            .jsonPath(ERRORS_JSON_PATH).doesNotExist()
            .jsonPath(EXTENSIONS_JSON_PATH).doesNotExist()
    }.onFailure {
        this.debugPrint()
    }.getOrThrow()
}

fun WebTestClient.ResponseSpec.debugPrint(): WebTestClient.ResponseSpec {
    println(this.returnResult<String>())
    return this
}

fun makeUUID(string: String = "string"): UUID = UUID.nameUUIDFromBytes(string.encodeToByteArray())
