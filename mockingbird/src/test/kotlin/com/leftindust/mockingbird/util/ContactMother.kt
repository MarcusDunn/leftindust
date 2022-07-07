package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.contact.Contact
import com.leftindust.mockingbird.doctor.Doctor
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.patient.Patient_.ethnicity
import com.leftindust.mockingbird.patient.Patient_.insuranceNumber
import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.person.Relationship
import com.leftindust.mockingbird.person.Sex
import java.time.LocalDate
import java.time.Month
import java.util.UUID

object ContactMother {
    private const val `jenny's first name` = "Jenny"
    private const val `jenny's middle name` = "Ellis"
    private const val `jenny's last name` = "White"
    private val `jenny the doctors id` = UUID.fromString("77941cfc-7808-4c7a-bbbe-62c6a637d5f8")

    val jennyContactInfoUnpersisted
        get() = Contact(
            patient = null,
            relationship = Relationship.Parent,
            nameInfo = NameInfo(
                firstName = `jenny's first name`,
                lastName = `jenny's last name`,
                middleName = `jenny's middle name`
            ),
            phone = emptySet(),
            email = setOf(EmailMother.jennysEmail),
        )

    val jennyContactInfoPersisted
        get() = jennyContactInfoUnpersisted.apply { id = `jenny the doctors id` }
}