package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.NullEntityIdInConverterException
import com.leftindust.mockingbird.country.CountryState
import java.util.UUID


fun AddressEntity.toAddress(): Address {
    return AddressImpl(
        id = id ?: throw NullEntityIdInConverterException(this),
        type = type,
        address = address,
        city = city,
        countryState = countryState,
        postalCode = postalCode,
    )
}

private data class AddressImpl(
    override val id: UUID,
    override val type: AddressType,
    override var address: String,
    override var city: String,
    override var countryState: CountryState,
    override var postalCode: String
): Address