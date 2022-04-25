package com.leftindust.mockingbird.country

import com.expediagroup.graphql.server.operations.Query
import org.springframework.stereotype.Component

@Component
class CountryQuery : Query {
    fun country(country: GraphQLCountry): GraphQLProvince = country.associatedStates()
}