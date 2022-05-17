package com.leftindust.mockingbird.graphql.queries

import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Component

@Component
class HealthCheckQuery {
    @QueryMapping
    fun mockingbirdIsAlive() = true
}