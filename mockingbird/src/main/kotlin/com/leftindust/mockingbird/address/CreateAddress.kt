package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.country.Countries

interface CreateAddress {
    val addressType: AddressType
    val address: String
    val city: String
    val country: Countries
    val province: String
    val postalCode: String
}