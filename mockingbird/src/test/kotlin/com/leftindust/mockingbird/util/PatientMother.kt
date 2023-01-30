package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.address.AddressEntity
import com.leftindust.mockingbird.contact.Contact
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.DoctorPatientEntity
import com.leftindust.mockingbird.email.CreateEmailDto
import com.leftindust.mockingbird.email.EmailEntity
import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.graphql.types.delete
import com.leftindust.mockingbird.graphql.types.update
import com.leftindust.mockingbird.patient.*
import com.leftindust.mockingbird.person.*
import com.leftindust.mockingbird.phone.CreatePhoneDto
import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.survey.link.SurveyLinkEntity
import com.leftindust.mockingbird.user.MediqUser
import com.leftindust.mockingbird.util.AddressMother.DansHouse
import com.leftindust.mockingbird.util.EmailMother.DansEmail
import dev.forkhandles.result4k.onFailure
import java.time.LocalDate
import java.time.Month
import java.util.*

object PatientMother {


    object Dan {
        const val firstName = "Dan"
        const val newFirstName = "Jack"
        val middleName = "TheMan"
        val newMiddleName = ""
        const val lastName = "Shervershani"
        const val newLastName = "Alexander"
        val dateOfBirth = LocalDate.of(2014, Month.MARCH, 12)
        val newDateOfBirth = LocalDate.of(2001, Month.APRIL, 22)
        val id = UUID.fromString("3444970a-3e31-11ed-b878-0242ac120002")
        val graphqlId = PatientDto.PatientDtoId(id)
        val user: MediqUser? = null
        val thumbnail: ByteArray? = null
        val sex = Sex.Male
        val gender: String = "female"
        val newGender: String = "Male"
        val ethnicity: Ethnicity? = null
        val newEthnicity: Ethnicity = Ethnicity.White
        val insuranceNumber: String? = null
        val newInsuranceNumber: String = "120938457"
        val contacts: MutableSet<Contact>
            get() = mutableSetOf()
        val doctors: MutableSet<DoctorPatientEntity>
            get() = mutableSetOf()
        val emailsDetached: MutableSet<EmailEntity>
            get() = mutableSetOf(DansEmail.entityDetached)
        val emailsTransient: MutableSet<EmailEntity>
            get() = mutableSetOf(DansEmail.entityTransient)
        val phonesDetached: MutableSet<Phone>
            get() = mutableSetOf(PhoneMother.DansCell.entityDetached)
        val phonesTransient: MutableSet<Phone>
            get() = mutableSetOf(PhoneMother.DansCell.entityTransient)
        val assignedSurveysTransient: MutableSet<SurveyLinkEntity>
            get() = mutableSetOf()

        val assignedSurveysDetached: MutableSet<SurveyLinkEntity>
            get() = mutableSetOf()
        val addressesTransient: MutableSet<AddressEntity>
            get() = mutableSetOf(DansHouse.entityTransient)
        val addressesDetached: MutableSet<AddressEntity>
            get() = mutableSetOf(DansHouse.entityDetached)
        val events: MutableSet<PatientEventEntity>
            get() = mutableSetOf()

        val nameInfoId = UUID.fromString("e257f4f5-15c5-4375-a99b-da6354e4d0b5")
        val entityDetached: PatientEntity
            get() = PatientEntity(
                nameInfoEntity = NameInfoEntity(
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
            nameInfoEntity = NameInfoEntity(
                firstName = firstName,
                lastName = lastName,
                middleName = middleName
            ).apply { id = nameInfoId },
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

        val createPatientDto: CreatePatientDto
            get() = CreatePatientDto(
                nameInfo = CreateNameInfoDto(
                    firstName = firstName,
                    lastName = lastName,
                    middleName = middleName
                ),
                addresses = listOf(DansHouse.createDto),
                emails = listOf(DansEmail.createDto),
                phones = listOf(PhoneMother.DansCell.createDto),
                thumbnail = "",
                sex = Sex.Male,
                dateOfBirth = dateOfBirth,
                gender = gender,
                ethnicity = ethnicity,
                insuranceNumber = insuranceNumber,
                doctors = listOf(
                    DoctorDto.DoctorDtoId(
                        DoctorMother.Jenny.id
                    )
                ),
                emergencyContacts = listOf(ContactMother.Aydan.createDto)
            )

        val updatedContacts = listOf(ContactMother.Aydan.createUpdatedDto)

        fun updatePatientDto(pid: PatientDto.PatientDtoId = graphqlId): UpdatePatientDto {
            return object : UpdatePatientDto {
                override val pid = pid
                override val nameInfo = update(
                    object : UpdateNameInfoDto {
                        override val firstName = update(newFirstName)
                        override val lastName = update(newLastName)
                        override val middleName = Deletable.Update(newMiddleName)
                    }
                )
                override val addresses = update(listOf(AddressMother.JennysHouse.createDto))
                override val emails: Updatable<List<CreateEmailDto>> = update(listOf(DansEmail.createUpdatedDto))
                override val phones: Updatable<List<CreatePhoneDto>> =
                    update(listOf(PhoneMother.DansCell.createUpdatedDto))
                override val thumbnail = delete<String>()
                override val sex = update(Sex.Male)
                override val dateOfBirth = update(newDateOfBirth)
                override val gender = Deletable.Update(newGender)
                override val ethnicity = Deletable.Update(newEthnicity)
                override val insuranceNumber = Deletable.Update(newInsuranceNumber)
                override val doctors = update(
                    listOf(
                        DoctorDto.DoctorDtoId(
                            DoctorMother.Dan.id
                        )
                    )
                )
                override val emergencyContacts = update(updatedContacts)
            }
        }

        val entityUpdatedTransient: PatientEntity = PatientEntity(
            nameInfoEntity = NameInfoEntity(
                firstName = newFirstName,
                lastName = newLastName,
                middleName = newMiddleName
            ).apply { id = nameInfoId },
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

        val domain = entityDetached.toPatient().onFailure { throw it.reason.toMockingbirdException() }
        val updatedDomainEntityDetached =
            entityUpdatedTransient.toPatient().onFailure { throw it.reason.toMockingbirdException() }
        val dto: PatientDto = domain.toPatientDto().onFailure { throw it.reason.toMockingbirdException() }
        val updatedDto: PatientDto =
            updatedDomainEntityDetached.toPatientDto().onFailure { throw it.reason.toMockingbirdException() }

        val domainEntityTransient = entityTransient.toPatient().onFailure { throw it.reason.toMockingbirdException() }
        val createPatient: CreatePatient
            get() = createPatientDto.toCreatePatient().onFailure { throw it.reason.toMockingbirdException() }
    }
}