package com.leftindust.mockingbird.dao.entity

import com.leftindust.mockingbird.graphql.types.GraphQLAddressType
import com.leftindust.mockingbird.graphql.types.GraphQLCanadianProvince
import com.leftindust.mockingbird.graphql.types.GraphQLClinic
import com.leftindust.mockingbird.graphql.types.GraphQLCountry
import com.leftindust.mockingbird.graphql.types.input.GraphQLAddressEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLClinicEditInput
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class ClinicTest {

    @Test
    fun setByGqlInput() {
        val clinic = Clinic(
            name = "sir Aurthur Curry's hospice for terminal deaf and blind children",
            address = Address(
                type = GraphQLAddressType.Apartment,
                address = "442 2nd W",
                city = "New York",
                countryState = CountryState(
                    GraphQLCountry.Canada,
                    GraphQLCanadianProvince.Provinces.NewBrunswick.name
                ),
                postalCode = "fe3232",
            ),
            doctors = mutableSetOf(mockk())
        )

        clinic.setByGqlInput(
            GraphQLClinicEditInput(
                cid = GraphQLClinic.ID(UUID.randomUUID()),
                address = GraphQLAddressEditInput(
                    address = "main st",
                    addressType = GraphQLAddressType.Home
                )
            ), mockk()
        )

        assertEquals(
            Address(
                type = GraphQLAddressType.Home,
                address = "main st",
                city = "New York",
                countryState = CountryState(
                    GraphQLCountry.Canada,
                    GraphQLCanadianProvince.Provinces.NewBrunswick.name
                ),
                postalCode = "fe3232",
            ),
            clinic.address,
        )
    }
}