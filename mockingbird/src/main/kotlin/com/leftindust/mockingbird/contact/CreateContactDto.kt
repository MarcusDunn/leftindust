package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.email.CreateEmailDto
import com.leftindust.mockingbird.person.CreateNameInfo
import com.leftindust.mockingbird.person.CreateNameInfoDto
import com.leftindust.mockingbird.person.Relationship
import com.leftindust.mockingbird.phone.CreatePhoneDto

data class CreateContactDto(
    val nameInfo: CreateNameInfoDto,
    val relationship: Relationship,
    val phones: List<CreatePhoneDto>,
    val emails: List<CreateEmailDto>,
)

