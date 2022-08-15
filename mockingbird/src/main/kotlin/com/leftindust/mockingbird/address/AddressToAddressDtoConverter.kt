package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import org.springframework.stereotype.Component

@Component
class AddressToAddressDtoConverter : InfallibleConverter<Address, AddressDto> {
    override fun convert(source: Address): AddressDto {
        return AddressDto(
            id = AddressDto.AddressDtoId(source.id ?: throw NullEntityIdInConverterException(source)),
            type = source.type,
            address = source.address,
            city = source.city,
            country = source.countryState.country,
            province = source.countryState.province,
            postalCode = source.postalCode,
        )
    }

}