package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.contact.Contact
import com.leftindust.mockingbird.contact.ContactDto
import com.leftindust.mockingbird.contact.CreateContact
import com.leftindust.mockingbird.contact.CreateContactDto
import com.leftindust.mockingbird.contact.CreateContactGraphQlDto
import com.leftindust.mockingbird.email.CreateEmailDto
import com.leftindust.mockingbird.email.CreateEmailGraphQlDto
import com.leftindust.mockingbird.email.EmailType
import com.leftindust.mockingbird.email.toCreateEmail
import com.leftindust.mockingbird.patient.PatientEntity
import com.leftindust.mockingbird.person.CreateNameInfoDto
import com.leftindust.mockingbird.person.NameInfoEntity
import com.leftindust.mockingbird.person.Relationship
import com.leftindust.mockingbird.phone.CreatePhone
import com.leftindust.mockingbird.phone.CreatePhoneDto
import com.leftindust.mockingbird.phone.CreatePhoneGraphQlDto
import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.phone.PhoneType
import dev.forkhandles.result4k.onFailure
import java.util.UUID

object ContactMother {
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
        val patientDetached: PatientEntity
            get() = PatientMother.Dan.entityDetached
        val patientTransient: PatientEntity
            get() = PatientMother.Dan.entityDetached
        val entityDetached: Contact
            get() = Contact(
                patientEntity = patientDetached,
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

        val entityTransient: Contact
            get() = Contact(
                patientEntity = patientTransient,
                relationship = relationship,
                nameInfoEntity = NameInfoEntity(
                    firstName = firstName,
                    lastName = lastName,
                    middleName = middleName
                ),
                phone = phone,
                email = emailsTransient,
            )

        val createDto = CreateContactGraphQlDto(
            nameInfo = CreateNameInfoDto(
                firstName = firstName,
                middleName = middleName,
                lastName = lastName
            ),
            relationship = relationship,
            phones = listOf(
                CreatePhoneGraphQlDto(
                    number = "33425",
                    type = PhoneType.Home,
                )
            ),
            emails = listOf(
                CreateEmailGraphQlDto(
                    type = EmailType.Work,
                    email = "Emergency@gmail.com",
                )
            )
        )

        val createUpdatedDto = CreateContactGraphQlDto(
            nameInfo = CreateNameInfoDto(
                firstName = "Boris",
                middleName = "",
                lastName = "V"
            ),
            relationship = Relationship.Other,
            phones = listOf(
                CreatePhoneGraphQlDto(
                    number = "222334",
                    type = PhoneType.Home,
                )
            ),
            emails = listOf(
                CreateEmailGraphQlDto(
                    type = EmailType.Personal,
                    email = "newEmergency@gmail.com",
                )
            )
        )


        val createDomain: CreateContact
            get() = object : CreateContact {
                override val nameInfo = CreateNameInfoDto(
                    firstName = "Aydan",
                    middleName = null,
                    lastName = "White"
                )
                override val relationship = Relationship.Parent
                override val phones = emptyList<CreatePhone>()
                override val emails = listOf(EmailMother.DansEmail.createDomain)
            }
    }
}