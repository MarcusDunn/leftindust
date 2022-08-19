package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import java.util.UUID

data class EmailDto(
    override val id: EmailDtoId,
    val type: EmailType,
    val email: String,
) : AbstractGraphQLDto<EmailDto.EmailDtoId>() {
    data class EmailDtoId(override val value: UUID) : GraphQLID<UUID>
}
