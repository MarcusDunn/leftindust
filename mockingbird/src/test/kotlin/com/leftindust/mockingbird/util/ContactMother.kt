package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.contact.Contact
import com.leftindust.mockingbird.contact.ContactDto
import com.leftindust.mockingbird.contact.CreateContactDto
import com.leftindust.mockingbird.contact.CreateContactDtoToCreateContactConverter
import com.leftindust.mockingbird.email.CreateEmailDto
import com.leftindust.mockingbird.email.CreateEmailDtoToCreateEmailFallibleConverter
import com.leftindust.mockingbird.email.Email
import com.leftindust.mockingbird.patient.PatientEntity
import com.leftindust.mockingbird.person.CreateNameInfoDto
import com.leftindust.mockingbird.person.NameInfoEntity
import com.leftindust.mockingbird.person.Relationship
import com.leftindust.mockingbird.phone.Phone
import java.util.UUID

object ContactMother {
    private val createEmailDtoToEmailDtoConverter = CreateEmailDtoToCreateEmailFallibleConverter()
    private val CreateContactDtoToCreateContactConverter = CreateContactDtoToCreateContactConverter(createEmailDtoToEmailDtoConverter,)
    object Aydan {

        const val firstName = "Aydan"
        val middleName: String? = null
        const val lastName = "White"
        val id = UUID.fromString("180f97ca-1dbf-11ed-861d-0242ac120002")
        val graphqlId = ContactDto.ContactDtoId(id)
        val phone = emptySet<Phone>()
        val emailsTransient = setOf(EmailMother.DansEmail.entityTransient)
        val emailsDetached = setOf(EmailMother.DansEmail.entityDetached)
        val relationship = Relationship.Parent
        val patientDetached: PatientEntity = PatientMother.Dan.entityDetached
        val patientTransient: PatientEntity = PatientMother.Dan.entityDetached
        val entityDetached = Contact(
            relationship = relationship,
            nameInfoEntity = NameInfoEntity(
                firstName = firstName,
                lastName = lastName,
                middleName = middleName
            ),
            phone = phone,
            email = emailsDetached,
        ).apply {
            this.id = this@Aydan.id
        }

        val entityTransient = Contact(
            relationship = relationship,
            nameInfoEntity = NameInfoEntity(
                firstName = firstName,
                lastName = lastName,
                middleName = middleName
            ),
            phone = phone,
            email = emailsTransient,
        )

        val createDto = CreateContactDto(
            nameInfo = CreateNameInfoDto(
                firstName = "Aydan",
                middleName = null,
                lastName = "White"
            ),
            relationship = Relationship.Parent,
            phones = emptyList(),
            emails = listOf(EmailMother.DansEmail.createDto)
        )

        val createDomain = CreateContactDtoToCreateContactConverter.convert(createDto)!!
    }
}