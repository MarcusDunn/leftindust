package com.leftindust.mockingbird.dao.entity

import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.address.AddressType
import com.leftindust.mockingbird.address.UpdateAddressDto
import com.leftindust.mockingbird.clinic.Clinic
import com.leftindust.mockingbird.clinic.ClinicEditDto
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
                type = AddressType.Apartment,
                address = "442 2nd W",
                city = "New York",
                postalCode = "fe3232",
            ),
            doctors = mutableSetOf(mockk())
        )

        clinic.setByGqlInput(
            ClinicEditDto(
                cid = GraphQLClinic.ID(UUID.randomUUID()),
                address = UpdateAddressDto(
                    address = "main st",
                    addressType = AddressType.Home
                )
            ), mockk()
        )

        assertEquals(
            Address(
                type = AddressType.Home,
                address = "main st",
                city = "New York",
                postalCode = "fe3232",
            ),
            clinic.address,
        )
    }
}