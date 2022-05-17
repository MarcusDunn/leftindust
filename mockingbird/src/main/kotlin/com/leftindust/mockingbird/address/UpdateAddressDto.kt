package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.country.Countries
import com.leftindust.mockingbird.graphql.types.Updatable

data class UpdateAddressDto(
    override val addressType: Updatable<AddressType>,
    override val address: Updatable<String>,
    override val city: Updatable<String>,
    override val country: Updatable<Countries>,
    override val province: Updatable<String>,
    override val postalCode: Updatable<String>,
) : UpdateAddress