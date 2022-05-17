package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.country.CountryQueryController
import com.leftindust.mockingbird.country.CanadianProvince
import com.leftindust.mockingbird.country.Countries
import org.junit.jupiter.api.Test

internal class CountryQueryControllerTest {

    @Test
    fun country() {
        val countryQueryController = CountryQueryController()

        val result = countryQueryController.country(Countries.Canada).asStrings()

        assert(result.containsAll(CanadianProvince.Provinces.values().map { it.name }))
    }
}