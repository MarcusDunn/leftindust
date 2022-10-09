package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class ContactDtoConverter : InfallibleConverter<Contact, ContactDto> {
    override fun convert(source: Contact): ContactDto {
        val contactId = requireNotNull(source.id) { "Contact id cannot be null" }
        return ContactDto(
            id = ContactDto.ContactDtoId(contactId),
            firstName = source.nameInfoEntity.firstName,
            middleName = source.nameInfoEntity.middleName,
            lastName = source.nameInfoEntity.lastName,
            relationship = source.relationship,
        )
    }
}