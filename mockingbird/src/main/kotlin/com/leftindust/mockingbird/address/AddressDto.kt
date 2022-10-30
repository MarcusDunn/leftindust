package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.country.Countries
import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import java.util.UUID

data class AddressDto(
    override val id: AddressDtoId,
    val addressType: AddressType,
    val address: String,
    val city: String,
    val country: Countries,
    val province: String,
    val postalCode: String,
) : AbstractGraphQLDto<AddressDto.AddressDtoId>() {

    companion object {
        const val GRAPHQL_TYPE = "Address"
    }
    data class AddressDtoId(override val value: UUID) : GraphQLID<UUID>
}