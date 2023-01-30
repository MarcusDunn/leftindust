package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.country.CountryState
import java.util.UUID

interface Address {
    val id: UUID
    val type: AddressType
    var address: String
    var city: String
    var countryState: CountryState
    var postalCode: String
}