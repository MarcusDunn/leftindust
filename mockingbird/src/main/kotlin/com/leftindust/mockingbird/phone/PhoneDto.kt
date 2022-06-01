package com.leftindust.mockingbird.phone

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import java.util.UUID

data class PhoneDto(
    override val id: Id,
    val number: String,
    val type: PhoneType,
) : AbstractGraphQLDto<PhoneDto.Id>() {
    data class Id(override val value: UUID) : GraphQLID<UUID>
}