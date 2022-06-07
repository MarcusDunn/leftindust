package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import java.util.UUID


data class NameInfoDto(
    override val id: NameInfoDtoId,
    val firstName: String,
    val middleName: String?,
    val lastName: String,
) : AbstractGraphQLDto<NameInfoDto.NameInfoDtoId>() {
    data class NameInfoDtoId(override val value: UUID) : GraphQLID<UUID>
}
