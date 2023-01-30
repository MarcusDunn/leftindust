package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.country.Countries

interface CreateAddressDto {
    val addressType: AddressType
    val address: String
    val city: String
    val country: Countries
    val province: String
    val postalCode: String
}

fun CreateAddressDto.toCreateAddress(): CreateAddress {
    return CreateAddressImpl(
        addressType = addressType,
        address = address,
        city = city,
        country = country,
        province = province,
        postalCode = postalCode
    )
}

private data class CreateAddressImpl(
    override val addressType: AddressType,
    override val address: String,
    override val city: String,
    override val country: Countries,
    override val province: String,
    override val postalCode: String
) : CreateAddress

data class CreateAddressGraphQlDto(
    override val addressType: AddressType,
    override val address: String,
    override val city: String,
    override val country: Countries,
    override val province: String,
    override val postalCode: String
) : CreateAddressDto