package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.doctor.Doctor
import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.util.AddressMother.jennysHouse
import com.leftindust.mockingbird.util.EmailMother.jennysEmail
import com.leftindust.mockingbird.util.PhoneMother.jennysHomePhone
import com.leftindust.mockingbird.util.PhoneMother.jennysWorkPhone
import java.time.LocalDate
import java.time.Month
import java.util.UUID

object DoctorMother {
    private const val `jenny's first name` = "Jenny"
    private const val `jenny's middle name` = "Ellis"
    private const val `jenny's last name` = "White"
    private val `jenny's date of birth` = LocalDate.of(1924, Month.APRIL, 17)
    private val `jenny the doctors id` = UUID.fromString("77941cfc-7808-4c7a-bbbe-62c6a637d5f8")

    val jennyTheDoctorUnpersisted
        get() = Doctor(
            nameInfo = NameInfo(
                firstName = `jenny's first name`,
                lastName = `jenny's last name`,
                middleName = `jenny's middle name`
            ),
            addresses = mutableSetOf(jennysHouse),
            emails = mutableSetOf(jennysEmail),
            phones = mutableSetOf(jennysHomePhone, jennysWorkPhone),
            user = null,
            events = mutableSetOf(),
            thumbnail = null,
            title = null,
            dateOfBirth = `jenny's date of birth`,
            clinics = mutableSetOf(),
            patients = mutableSetOf(),
        )

    val jennyTheDoctorPersisted
        get() = jennyTheDoctorUnpersisted.apply { id = `jenny the doctors id` }
}