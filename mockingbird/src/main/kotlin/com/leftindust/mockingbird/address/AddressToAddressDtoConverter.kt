package com.leftindust.mockingbird.address

fun Address.toAddressDto(): AddressDto {
    return AddressDto(
        id = AddressDto.AddressDtoId(id),
        addressType = type,
        address = address,
        city = city,
        country = countryState.country,
        province = countryState.province,
        postalCode = postalCode,
    )
}