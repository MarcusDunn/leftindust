package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.contact.Contact
import com.leftindust.mockingbird.doctor.DoctorPatientEntity
import com.leftindust.mockingbird.email.Email
import com.leftindust.mockingbird.graphql.types.update
import com.leftindust.mockingbird.patient.*
import com.leftindust.mockingbird.person.CreateNameInfoDto
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.person.Sex
import com.leftindust.mockingbird.person.UpdateNameInfoDto
import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.survey.link.SurveyLinkEntity
import com.leftindust.mockingbird.user.MediqUser
import com.leftindust.mockingbird.util.AddressMother.DansHouse
import com.leftindust.mockingbird.util.EmailMother.DansEmail
import java.time.LocalDate
import java.time.Month
import java.util.UUID

object PatientMother {
    val patientToPatientDtoConverter = PatientToPatientDtoConverter()
    val patientEntityToPatientConverter = PatientEntityToPatientConverter()

    object Dan {
        const val firstName = "Dan"
        val middleName = "TheMan"
        const val lastName = "Shervershani"
        val dateOfBirth = LocalDate.of(2014, Month.MARCH, 12)
        val id = UUID.fromString("3444970a-3e31-11ed-b878-0242ac120002")
        val graphqlId = PatientDto.PatientDtoId(id)
        val user: MediqUser? = null
        val thumbnail: ByteArray? = null
        val sex = Sex.Male
        val gender: String = "female"
        val ethnicity: Ethnicity? = null
        val insuranceNumber: String? = null
        val contacts: MutableSet<Contact>
            get() = mutableSetOf()
        val doctors: MutableSet<DoctorPatientEntity>
            get() = mutableSetOf()
        val emailsDetached: MutableSet<Email>
            get() = mutableSetOf(DansEmail.entityDetached)
        val emailsTransient: MutableSet<Email>
            get() = mutableSetOf(DansEmail.entityTransient)
        val phonesDetached: MutableSet<Phone>
            get() = mutableSetOf(PhoneMother.DansCell.entityDetached)
        val phonesTransient: MutableSet<Phone>
            get() = mutableSetOf(PhoneMother.DansCell.entityTransient)
        val addressesDetached: MutableSet<Address>
            get() = mutableSetOf(DansHouse.entityDetached)

        val assignedSurveysTransient: MutableSet<SurveyLinkEntity>
            get() = mutableSetOf()
        val assignedSurveysDetached: MutableSet<SurveyLinkEntity>
            get() = mutableSetOf()

        val addressesTransient: MutableSet<Address>
            get() = mutableSetOf(DansHouse.entityTransient)
        val events: MutableSet<PatientEventEntity>
            get() = mutableSetOf()
        val nameInfoId = UUID.fromString("e257f4f5-15c5-4375-a99b-da6354e4d0b5")
        val entityDetached: PatientEntity
            get() = PatientEntity(
                nameInfo = NameInfo(
                    firstName = firstName,
                    lastName = lastName,
                    middleName = middleName
                ).apply { id = nameInfoId },
                addresses = addressesDetached,
                emails = emailsDetached,
                phones = phonesDetached,
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
                assignedSurveys = assignedSurveysDetached
            ).apply { id = this@Dan.id }
        val entityTransient: PatientEntity = PatientEntity(
            nameInfo = NameInfo(
                firstName = firstName,
                lastName = lastName,
                middleName = middleName
            ),
            addresses = addressesTransient,
            emails = emailsTransient,
            phones = phonesTransient,
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
            assignedSurveys = assignedSurveysTransient
        ).apply { id = this@Dan.id }

        val updatePatientDto: UpdatePatientDto = UpdatePatientDto(
            nameInfo = update(UpdateNameInfoDto(
                firstName = update("Dann"),
                lastName = "TheDan",
                middleName = "Servershani"
            )),
            addresses = addressesTransient,
            emails = emailsTransient,
            phones = phonesTransient,
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
            assignedSurveys = assignedSurveysTransient
        ).apply { id = this@Dan.id }

        val entityUpdatedTransient: PatientEntity = PatientEntity(
            nameInfo = NameInfo(
                firstName = "Dann",
                lastName = "TheDan",
                middleName = "Servershani"
            ),
            addresses = addressesTransient,
            emails = emailsTransient,
            phones = phonesTransient,
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
            assignedSurveys = assignedSurveysTransient
        ).apply { id = this@Dan.id }

        // TODO
        val createPatientDto = CreatePatientDto(
            nameInfo = CreateNameInfoDto(
                firstName = "Dann",
                lastName = "TheDan",
                middleName = "Servershani"
            ),
            addresses = listOf(DansHouse.createAddressDto),
            emails = emailsTransient,
            phones = phonesTransient,
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
            assignedSurveys = assignedSurveysTransient
        )

        val domainEntityDetached = patientEntityToPatientConverter.convert(entityDetached)
        val updatedDomainEntityDetached = patientEntityToPatientConverter.convert(entityUpdatedTransient)
        val dto: PatientDto = patientToPatientDtoConverter.convert(domainEntityDetached)
        val updatedDto: PatientDto = patientToPatientDtoConverter.convert(updatedDomainEntityDetached)

        // TODO convert CreatePatientDto to CreatePatient domain object (val createPatient)

        val domainEntityTransient = patientEntityToPatientConverter.convert(entityTransient)
    }
}