package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.email.CreateEmailDto
import com.leftindust.mockingbird.email.CreateEmailGraphQlDto
import com.leftindust.mockingbird.person.CreateNameInfoDto
import com.leftindust.mockingbird.person.Relationship
import com.leftindust.mockingbird.phone.CreatePhoneDto
import com.leftindust.mockingbird.phone.CreatePhoneGraphQlDto

interface CreateContactDto {
    val nameInfo: CreateNameInfoDto
    val relationship: Relationship
    val phones: List<CreatePhoneDto>
    val emails: List<CreateEmailDto>
}

data class CreateContactGraphQlDto(
    override val nameInfo: CreateNameInfoDto,
    override val relationship: Relationship,
    override val phones: List<CreatePhoneGraphQlDto>,
    override val emails: List<CreateEmailGraphQlDto>,
) : CreateContactDto

