package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.contact.Contact
import com.leftindust.mockingbird.contact.ContactDto
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.person.Relationship
import com.leftindust.mockingbird.phone.Phone
import java.util.UUID

object ContactMother {
    object Aydan {
        const val firstName = "Aydan"
        val middleName: String? = null
        const val lastName = "White"
        val id = UUID.fromString("77941cfc-7808-4c7a-bbbe-62c6a637d5f8")
        val graphqlId = ContactDto.ContactDtoId(id)
        val phone = emptySet<Phone>()
        val email = setOf(EmailMother.jennysEmail)
        val relationship = Relationship.Parent
        val patient: Patient? = null
        val entityPersisted = Contact(
            patient = patient,
            relationship = relationship,
            nameInfo = NameInfo(
                firstName = firstName,
                lastName = lastName,
                middleName = middleName
            ),
            phone = phone,
            email = email,
        ).apply { id = this@Aydan.id }
    }
}