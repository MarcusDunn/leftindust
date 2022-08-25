package com.leftindust.mockingbird.phone

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import java.util.UUID

data class PhoneDto(
    override val id: PhoneDtoId,
    val number: String,
    val type: PhoneType,
) : AbstractGraphQLDto<PhoneDto.PhoneDtoId>() {
    data class PhoneDtoId(override val value: UUID) : GraphQLID<UUID>
}