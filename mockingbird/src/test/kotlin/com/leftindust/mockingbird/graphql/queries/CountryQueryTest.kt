package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.country.CountryQuery
import com.leftindust.mockingbird.country.GraphQLCanadianProvince
import com.leftindust.mockingbird.country.GraphQLCountry
import org.junit.jupiter.api.Test

internal class CountryQueryTest {

    @Test
    fun country() {
        val countryQuery = CountryQuery()

        val result = countryQuery.country(GraphQLCountry.Canada).asStrings()

        assert(result.containsAll(GraphQLCanadianProvince.Provinces.values().map { it.name }))
    }
}