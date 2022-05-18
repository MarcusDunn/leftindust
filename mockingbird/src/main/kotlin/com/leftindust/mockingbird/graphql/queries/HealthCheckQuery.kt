package com.leftindust.mockingbird.graphql.queries

import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller

@Controller
class HealthCheckQuery {
    @QueryMapping
    fun mockingbirdIsAlive() = true
}