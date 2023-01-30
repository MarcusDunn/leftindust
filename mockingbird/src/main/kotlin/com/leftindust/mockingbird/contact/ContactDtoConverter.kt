package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success


fun Contact.toContactDto(): Result4k<ContactDto, ConversionError<Contact, ContactDto>> {
    val contactId = requireNotNull(id) { "Contact id cannot be null" }
    return Success(
        ContactDto(
            id = ContactDto.ContactDtoId(contactId),
            firstName = nameInfoEntity.firstName,
            middleName = nameInfoEntity.middleName,
            lastName = nameInfoEntity.lastName,
            relationship = relationship,
        )
    )
}