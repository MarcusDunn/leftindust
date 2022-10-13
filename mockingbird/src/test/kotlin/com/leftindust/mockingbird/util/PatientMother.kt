package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.contact.Contact
import com.leftindust.mockingbird.contact.CreateContactDto
import com.leftindust.mockingbird.country.Countries
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.DoctorPatientEntity
import com.leftindust.mockingbird.email.*
import com.leftindust.mockingbird.patient.*
import com.leftindust.mockingbird.person.*
import com.leftindust.mockingbird.phone.CreatePhoneDto
import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.phone.PhoneType
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
        val addressesTransient: MutableSet<Address>
            get() = mutableSetOf(DansHouse.entityTransient)
        val addressesDetached: MutableSet<Address>
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

        val createPatientDto = CreatePatientDto(
            nameInfo = CreateNameInfoDto(
                firstName = "Dan",
                lastName = "Shirvani",
                middleName = ""
            ),
            addresses = listOf(CreateAddressDto(
                addressType = AddressMother.JennysHouse.addressType,
                address = "",
                city = "",
                country = Countries.Canada,
                province = "",
                postalCode = ""
            )),
            emails = listOf(CreateEmailDto(
                type = EmailType.Personal,
                email = "NewEmail@gmail.com"
            )),
            phones = listOf(CreatePhoneDto(
                number = "111111111",
                type = PhoneType.Home
            )),
            thumbnail = "",
            sex = Sex.Male,
            dateOfBirth = dateOfBirth,
            gender = gender,
            ethnicity = ethnicity,
            insuranceNumber = insuranceNumber,
            doctors = listOf(
                DoctorDto.DoctorDtoId(
                DoctorMother.Jenny.id
            )),
            emergencyContacts = listOf(CreateContactDto(
                firstName = "Sep",
                middleName= "",
                lastName = "Radmehr",
                relationship = Relationship.Other,
                phones = listOf(CreatePhoneDto(
                    number = "778221",
                    type = PhoneType.Cell
                )),
                emails = listOf(CreateEmailDto(
                    type = EmailType.Personal,
                    email = "Emergency@gmail.com"
                ))
            ))
        )

        val updatePatientDto: UpdatePatientDto = UpdatePatientDto(
            pid = Dan.graphqlId,
            nameInfo = UpdateNameInfoDto(
                firstName = "Dann",
                lastName = "TheDan",
                middleName = "Servershani"
            ),
            addresses = listOf(CreateAddressDto(
                addressType = AddressMother.JennysHouse.addressType,
                address = "",
                city = "",
                country = Countries.Canada,
                province = "",
                postalCode = ""
            )),
            emails = listOf(DansEmail.createDto),

            phones = listOf(CreatePhoneDto(
                number = "111111111",
                type = PhoneType.Home
            )),
            thumbnail ="",
            sex = Sex.Male,
            dateOfBirth = LocalDate.of(2001, Month.MAY, 11),
            gender = "NewGender",
            ethnicity = Ethnicity.White,
            insuranceNumber = "NewInsurance",
            doctors = listOf(DoctorDto.DoctorDtoId(
                DoctorMother.Jenny.id
            )),
            emergencyContacts = listOf(ContactMother.Aydan.createDto),
        )

        val entityUpdatedTransient: PatientEntity = PatientEntity(
            nameInfoEntity = NameInfoEntity(
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



        val domain = patientEntityToPatientConverter.convert(entityDetached)
        val updatedDomainEntityDetached = patientEntityToPatientConverter.convert(entityUpdatedTransient)
        val dto: PatientDto = patientToPatientDtoConverter.convert(domain)
        val updatedDto: PatientDto = patientToPatientDtoConverter.convert(updatedDomainEntityDetached)

        val domainEntityTransient = patientEntityToPatientConverter.convert(entityTransient)
        val createPatient = createPatientDto.toCreatePatient()

    }
}