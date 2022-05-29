package com.leftindust.mockingbird.graphql.queries

import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class HealthCheckQueryController {
    @QueryMapping
    fun mockingbirdIsAlive() = true
}