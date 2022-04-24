package com.leftindust.mockingbird.graphql.queries

import org.junit.jupiter.api.Test

internal class HealthCheckQueryTest {
    @Test
    fun mockingbirdIsAlive() {
        val healthCheckQuery = HealthCheckQuery()
        assert(healthCheckQuery.mockingbirdIsAlive())
    }
}