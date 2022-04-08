package com.leftindust.mockingbird.graphql.queries

import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.graphql.types.GraphQLCountry
import com.leftindust.mockingbird.graphql.types.GraphQLProvince
import org.springframework.stereotype.Component

@Component
class CountryQuery : Query {
    fun country(country: GraphQLCountry): GraphQLProvince = country.associatedStates()
}