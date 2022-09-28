package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.doctor.*
import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.user.MediqUser
import com.leftindust.mockingbird.util.AddressMother.JennysHouse
import com.leftindust.mockingbird.util.PhoneMother.JennysHomePhone
import com.leftindust.mockingbird.util.PhoneMother.JennysWorkPhone
import java.time.LocalDate
import java.time.Month
import java.util.UUID

object DoctorMother {
    val doctorToDoctorDto = DoctorToDoctorDtoConverter()
    val doctorEntityToDoctorConverter = DoctorEntityToDoctorConverter()

    object Jenny {
        const val firstName = "Jenny"
        const val middleName = "Ellis"
        const val lastName = "White"
        val dateOfBirth = LocalDate.of(1924, Month.APRIL, 17)
        val id = UUID.fromString("77941cfc-7808-4c7a-bbbe-62c6a637d5f8")
        val graphqlId = DoctorDto.DoctorDtoId(id)
        val addresses = mutableSetOf(JennysHouse.entityPersisted)
        val emails = mutableSetOf(EmailMother.jennysEmail)
        val phones = mutableSetOf(JennysHomePhone.entityPersisted, JennysWorkPhone.entityPersisted)
        val user: MediqUser? = null
        val events = mutableSetOf<DoctorEventEntity>()
        val thumbnail: ByteArray? = null
        val title: String? = null
        val clinics = mutableSetOf(ClinicMother.DansClinic.entityPersisted)
        val patients = mutableSetOf<DoctorPatientEntity>()
        val entityPersisted = DoctorEntity(
            nameInfo = NameInfo(
                firstName = firstName,
                lastName = lastName,
                middleName = middleName
            ),
            addresses = addresses,
            emails = emails,
            phones = phones,
            user = user,
            events = events,
            thumbnail = thumbnail,
            title = title,
            dateOfBirth = dateOfBirth,
            clinics = mutableSetOf(),
            patients = patients,
        ).apply {
            id = this@Jenny.id
            clinics = this@Jenny.clinics.map { ClinicDoctorEntity(it, this) }.toMutableSet()
        }

        val domain = doctorEntityToDoctorConverter.convert(entityPersisted)
        val dto = doctorToDoctorDto.convert(domain)
    }

    object Dan {
        const val firstName = "Dan"
        const val middleName = "TheGoat"
        const val lastName = "Shirvani"
        val dateOfBirth = LocalDate.of(2016, Month.APRIL, 17)
        val id = UUID.fromString("c7c89079-ee58-4187-9d31-1fab272aa7f0")
        val graphqlId = DoctorDto.DoctorDtoId(id)
        val addresses = mutableSetOf(AddressMother.DansHouse.entityDetached)
        val emails = mutableSetOf(EmailMother.dansEmailEntity)
        val phones = mutableSetOf(PhoneMother.DansCell.entityDetached)
        val user: MediqUser? = null
        val events = mutableSetOf<DoctorEventEntity>()
        val thumbnail: ByteArray? = null
        val title: String? = null
        val clinics = mutableSetOf<ClinicDoctorEntity>()
        val patients = mutableSetOf<DoctorPatientEntity>()
        val entityPersisted = DoctorEntity(
            nameInfo = NameInfo(
                firstName = firstName,
                lastName = lastName,
                middleName = middleName
            ),
            addresses = addresses,
            emails = emails,
            phones = phones,
            user = user,
            events = events,
            thumbnail = thumbnail,
            title = title,
            dateOfBirth = dateOfBirth,
            clinics = clinics,
            patients = patients,
        ).apply { id = this@Dan.id }

        val domain = (doctorEntityToDoctorConverter.convert(entityPersisted))
        val dto = doctorToDoctorDto.convert(domain)
    }

}