package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import java.util.UUID

data class EmailDto(
    override val id: Id,
    val type: EmailType,
    val email: String,
) : AbstractGraphQLDto<EmailDto.Id>() {
    data class Id(override val value: UUID) : GraphQLID<UUID>
}
