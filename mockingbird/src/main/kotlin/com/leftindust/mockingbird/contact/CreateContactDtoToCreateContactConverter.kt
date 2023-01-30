package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.email.toCreateEmail
import com.leftindust.mockingbird.person.CreateNameInfo
import com.leftindust.mockingbird.person.Relationship
import com.leftindust.mockingbird.phone.CreatePhone
import com.leftindust.mockingbird.phone.toCreatePhone
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.onFailure

fun CreateContactDto.toCreateContact(): Result4k<CreateContact, ConversionError<CreateContactDto, CreateContact>> {
    return Success(
        CreateContactImpl(
            nameInfo = nameInfo,
            relationship = relationship,
            phones = phones.map { it.toCreatePhone().onFailure { throw it.reason.toMockingbirdException() } },
            emails = emails.map { it.toCreateEmail().onFailure { throw it.reason.toMockingbirdException() } },
        )
    )
}

private data class CreateContactImpl(
    override val nameInfo: CreateNameInfo,
    override val relationship: Relationship,
    override val phones: List<CreatePhone>,
    override val emails: List<CreateEmail>,
) : CreateContact