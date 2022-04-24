package com.leftindust.mockingbird.graphql.queries

import com.expediagroup.graphql.server.operations.Query
import org.springframework.stereotype.Component

@Component
class HealthCheckQuery: Query {
    fun mockingbirdIsAlive() = true
}