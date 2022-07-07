package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.person.Sex
import java.time.LocalDate
import java.time.Month
import java.util.UUID

object PatientMother {
    private const val `jenny's first name` = "Jenny"
    private const val `jenny's middle name` = "Ellis"
    private const val `jenny's last name` = "White"
    private val `jenny's date of birth` = LocalDate.of(1924, Month.APRIL, 17)
    private val `jenny the doctors id` = UUID.fromString("77941cfc-7808-4c7a-bbbe-62c6a637d5f8")

    val jennyThePatientUnpersisted
        get() = Patient(
            nameInfo = NameInfo(
                firstName = `jenny's first name`,
                lastName = `jenny's last name`,
                middleName = `jenny's middle name`
            ),
            addresses = mutableSetOf(AddressMother.jennysHouse),
            emails = mutableSetOf(EmailMother.jennysEmail),
            phones = mutableSetOf(PhoneMother.jennysHomePhone, PhoneMother.jennysWorkPhone),
            events = mutableSetOf(),
            user = null,
            thumbnail = null,
            sex = Sex.Female,
            dateOfBirth = `jenny's date of birth`,
            gender = "female",
            ethnicity = null,
            insuranceNumber = null,
            contacts = mutableSetOf(),
            doctors = mutableSetOf(),
        )

    val jennyThePatientPersisted
        get() = jennyThePatientUnpersisted.apply { id = `jenny the doctors id` }
}