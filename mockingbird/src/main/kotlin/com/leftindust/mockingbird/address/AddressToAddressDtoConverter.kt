package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.NullEntityIdInConverterException


fun Address.toAddressDto(): AddressDto {
    return AddressDto(
        id = AddressDto.AddressDtoId(id ?: throw NullEntityIdInConverterException(this)),
        type = type,
        address = address,
        city = city,
        country = countryState.country,
        province = countryState.province,
        postalCode = postalCode,
    )
}