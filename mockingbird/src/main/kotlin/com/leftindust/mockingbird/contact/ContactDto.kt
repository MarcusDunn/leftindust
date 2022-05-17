package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.email.EmailDto
import com.leftindust.mockingbird.email.ReadEmailService
import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import com.leftindust.mockingbird.person.Relationship
import com.leftindust.mockingbird.phone.ReadPhoneService
import com.leftindust.mockingbird.phone.PhoneDto
import graphql.schema.DataFetchingEnvironment
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class ContactDto(
    override val id: Id,
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val relationship: Relationship,
) : AbstractGraphQLDto<ContactDto.Id>() {
    data class Id(override val value: UUID) : GraphQLID<UUID>

}