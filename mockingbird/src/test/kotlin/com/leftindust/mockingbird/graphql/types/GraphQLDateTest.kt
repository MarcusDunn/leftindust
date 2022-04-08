package com.leftindust.mockingbird.graphql.types

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class GraphQLDateTest {
    @Test
    internal fun `month transformations are inverse`() {
        for (graphQLMonth in GraphQLMonth.values()) {
            val localDate = LocalDate.of(2000, graphQLMonth.toJavaMonth(), 1)
            assertEquals(GraphQLDate(localDate).month, graphQLMonth)
        }
    }
}