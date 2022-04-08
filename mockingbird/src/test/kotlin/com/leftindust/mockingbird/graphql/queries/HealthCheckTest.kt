package com.leftindust.mockingbird.graphql.queries

import org.junit.jupiter.api.Test

internal class HealthCheckTest {
    @Test
    fun mockingbirdIsAlive() {
        val healthCheck = HealthCheck()
        assert(healthCheck.mockingbirdIsAlive())
    }
}