package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.contact.Contact
import com.leftindust.mockingbird.doctor.DoctorPatientEntity
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.PatientEventEntity
import com.leftindust.mockingbird.patient.PatientToPatientDtoConverter
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.person.Sex
import com.leftindust.mockingbird.user.MediqUser
import com.leftindust.mockingbird.util.AddressMother.DansHouse
import com.leftindust.mockingbird.util.EmailMother.DansEmail
import java.time.LocalDate
import java.time.Month
import java.util.UUID

object PatientMother {
    val patientToPatientDtoConverter = PatientToPatientDtoConverter()

    object Dan {
        const val firstName = "Dan"
        val middleName = null
        const val lastName = "Shervershani"
        val dateOfBirth = LocalDate.of(2014, Month.MARCH, 12)
        val id = UUID.fromString("f6154fa2-c662-4986-a0d8-e79883cbfdc1")
        val graphqlId = PatientDto.PatientDtoId(id)
        val user: MediqUser? = null
        val thumbnail: ByteArray? = null
        val sex = Sex.Male
        val gender: String = "female"
        val ethnicity: Ethnicity? = null
        val insuranceNumber: String? = null
        val contacts: MutableSet<Contact> = mutableSetOf()
        val doctors: MutableSet<DoctorPatientEntity> = mutableSetOf()
        val emails = mutableSetOf(DansEmail.entityPersisted)
        val phones = mutableSetOf(PhoneMother.DansCell.entityPersisted)
        val addresses = mutableSetOf(DansHouse.entityPersisted)
        val events: MutableSet<PatientEventEntity> = mutableSetOf()

        val entityPersisted
            get() = Patient(
                nameInfo = NameInfo(
                    firstName = firstName,
                    lastName = lastName,
                    middleName = middleName
                ),
                addresses = addresses,
                emails = emails,
                phones = phones,
                events = events,
                user = user,
                thumbnail = thumbnail,
                sex = sex,
                dateOfBirth = dateOfBirth,
                gender = gender,
                ethnicity = ethnicity,
                insuranceNumber = insuranceNumber,
                contacts = contacts,
                doctors = doctors,
            ).apply { id = this@Dan.id }

        val entityUnpersisted
            get() = entityPersisted.apply {
                id = null
                addresses.forEach { it.id = null }
                emails.forEach { it.id = null }
                phones.forEach { it.id = null }
                events.clear()
                doctors.clear()
                contacts.forEach { it.id = null }
            }

        val dto: PatientDto = patientToPatientDtoConverter.convert(entityPersisted)
    }

}