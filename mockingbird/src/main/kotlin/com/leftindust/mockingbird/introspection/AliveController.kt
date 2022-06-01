package com.leftindust.mockingbird.introspection

import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class AliveController {
    @QueryMapping
    fun isAlive(): Boolean = true
}