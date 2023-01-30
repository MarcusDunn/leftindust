package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.country.Countries
import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable

interface UpdateAddress {
    val addressType: Deletable<AddressType>
    val address: Updatable<String>
    val city: Updatable<String>
    val country: Updatable<Countries>
    val province: Updatable<String>
    val postalCode: Updatable<String>
}