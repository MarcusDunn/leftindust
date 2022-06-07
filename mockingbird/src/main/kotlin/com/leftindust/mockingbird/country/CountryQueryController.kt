package com.leftindust.mockingbird.country

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class CountryQueryController {
    @QueryMapping
    suspend fun country(@Argument country: Countries): Province = country.associatedStates()
}