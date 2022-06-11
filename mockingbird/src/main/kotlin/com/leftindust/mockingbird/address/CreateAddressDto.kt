package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.country.Countries

data class CreateAddressDto(
    override val addressType: AddressType,
    override val address: String,
    override val city: String,
    override val country: Countries,
    override val province: String,
    override val postalCode: String,
) : CreateAddress