package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.address.AddressType
import com.leftindust.mockingbird.clinic.Clinic
import com.leftindust.mockingbird.country.Countries
import com.leftindust.mockingbird.country.CountryState
import java.util.UUID

object ClinicMother {
    fun clinic(id: UUID? = null) = Clinic(
        name = "my clinic",
        address = Address(
            type = AddressType.Home,
            address = "123 Main Street",
            city = "Vancouver",
            countryState = CountryState(Countries.Canada, "BC"),
            postalCode = "ABV123"
        )
    ).apply { this.id = id }
}