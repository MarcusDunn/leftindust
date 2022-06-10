package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import com.leftindust.mockingbird.person.Relationship
import java.util.UUID

data class ContactDto(
    override val id: ContactDtoId,
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val relationship: Relationship,
) : AbstractGraphQLDto<ContactDto.ContactDtoId>() {
    data class ContactDtoId(override val value: UUID) : GraphQLID<UUID>

}