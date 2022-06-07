package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.email.CreateEmailDto
import com.leftindust.mockingbird.person.Relationship
import com.leftindust.mockingbird.phone.CreatePhoneDto

data class CreateContactDto(
    override val firstName: String,
    override val middleName: String?,
    override val lastName: String,
    override val relationship: Relationship,
    override val phones: List<CreatePhoneDto>,
    override val emails: List<CreateEmailDto>,
) : CreateContact

