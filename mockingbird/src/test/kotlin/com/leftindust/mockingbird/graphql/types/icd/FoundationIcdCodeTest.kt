package com.leftindust.mockingbird.graphql.types.icd

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class FoundationIcdCodeTest {
    @Test
    internal fun parsesUrlCorrectly() {
        val inputs = listOf(
            "http://id.who.int/icd/release/11/2020-09/mms/236924369/unspecified",
            "http://id.who.int/icd/release/11/2020-09/mms/1294771889",
            "http://id.who.int/icd/release/11/2020-09/mms/565740065/other",
        )

        val results = inputs.map { GraphQLFoundationIcdCode(it).code }

        val expected = listOf(
            "236924369",
            "1294771889",
            "565740065",
        )

        assertEquals(expected, results)
    }
}