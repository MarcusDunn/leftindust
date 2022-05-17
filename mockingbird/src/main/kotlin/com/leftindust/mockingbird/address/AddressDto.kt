package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.country.Countries
import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import java.util.UUID

data class AddressDto(
    override val id: Id,
    val type: AddressType?,
    val address: String,
    val city: String,
    val country: Countries,
    val province: String,
    val postalCode: String,
) : AbstractGraphQLDto<AddressDto.Id>() {
    data class Id(override val value: UUID) : GraphQLID<UUID>
}