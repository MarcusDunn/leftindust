package com.leftindust.mockingbird.util

import com.google.gson.JsonObject
import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.address.AddressType
import com.leftindust.mockingbird.contact.CreateContactDto
import com.leftindust.mockingbird.country.CanadianProvince
import com.leftindust.mockingbird.country.Countries
import com.leftindust.mockingbird.email.EmailType
import com.leftindust.mockingbird.record.RecordType
import com.leftindust.mockingbird.doctor.Doctor
import com.leftindust.mockingbird.email.Email
import com.leftindust.mockingbird.event.Event
import com.leftindust.mockingbird.event.CreateEventDto
import com.leftindust.mockingbird.extensions.gqlID
import com.leftindust.mockingbird.survey.*
import com.leftindust.mockingbird.graphql.types.*
import com.leftindust.mockingbird.graphql.types.input.*
import com.leftindust.mockingbird.group.MediqGroup
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.CreatePatientDto
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.person.*
import com.leftindust.mockingbird.phone.CreatePhoneDto
import com.leftindust.mockingbird.phone.PhoneType
import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.record.CreateRecordDto
import com.leftindust.mockingbird.record.MediqRecord
import com.leftindust.mockingbird.user.MediqUser
import java.sql.Date
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.util.*

object EntityStore {
    fun patient(testName: String = "no name given") = Patient(
        nameInfo = NameInfo(
            firstName = "marcus",
            middleName = testName,
            lastName = "dunn",
        ),
        dateOfBirth = Date.valueOf(LocalDate.of(2020, 1, 2)),
        addresses = setOf(
            Address(
                type = AddressType.Home,
                address = "874 West 1st Street",
                city = "North Vancouver",
                postalCode = "y7h1p4",
            )
        ),
        emails = setOf(Email(email = "hello@world.ca", type = EmailType.Personal)),
        phones = setOf(Phone("6632231111", PhoneType.Home)),
        sex = Sex.Male,
        gender = Sex.Male.name,
        ethnicity = Ethnicity.White,
        insuranceNumber = "marcus",
        contacts = emptySet(),
        doctors = mutableSetOf(),
    )

    fun doctor(testName: String) = Doctor(
        nameInfo = NameInfo(
            firstName = "dan",
            lastName = testName,
            middleName = "the man",
        ),
        dateOfBirth = Date.valueOf(LocalDate.of(2018, 1, 24)),
        addresses = setOf(
            Address(
                type = AddressType.Home,
                address = "999 East 7th Drive",
                city = "West Vancouver",
                postalCode = "y7h1p5",
            )
        ),
        emails = setOf(Email(email = "world@hello.ca", type = EmailType.Personal)),
        phones = setOf(Phone("6632231211", PhoneType.Home)),
        title = "sir",
        patients = mutableSetOf(),
        schedule = emptySet(),
    )

    fun graphQLPatientInput(testName: String) = CreatePatientDto(
        nameInfo = CreateNameInfoDto(
            firstName = "aydan",
            middleName = testName,
            lastName = "gaite",
        ),
        phones = listOf(
            CreatePhoneDto(
                number = "11111111",
                type = PhoneType.Work,
            )
        ),
        dateOfBirth = GraphQLDateInput(
            day = 12,
            month = GraphQLMonth.Apr,
            year = 1948
        ),
        addresses = listOf(
            CreateAddressDto(
                addressType = AddressType.Home,
                address = "6732 main st",
                city = "East Vancouver",
                country = Countries.Canada,
                province = CanadianProvince.Provinces.NewBrunswick.name,
                postalCode = "h221234",
            )
        ),
        emails = listOf(
            GraphQLEmailInput(
                type = EmailType.School,
                email = "hello@mars.ca",
            )
        ),
        insuranceNumber = gqlID(111111111),
        sex = Sex.Male,
        ethnicity = Ethnicity.AmericanAboriginal,
        emergencyContacts = listOf(
            CreateContactDto(
                firstName = "mom firstName",
                middleName = "mom middleName",
                lastName = "mom lastName",
                relationship = Relationship.Parent,
                phones = listOf(
                    CreatePhoneDto(
                        number = "111111111",
                        type = PhoneType.Work,
                    ),
                    CreatePhoneDto(
                        number = "223223222",
                        type = PhoneType.Home,
                    ),
                ),
                emails = listOf(
                    GraphQLEmailInput(
                        type = EmailType.School,
                        email = "bye@saturn.uk",
                    )
                )
            )
        ),
    )

    fun graphQLEventInput(testName: String) =
        CreateEventDto(
            title = testName,
            description = "some description",
            start = GraphQLUtcTime(Timestamp.valueOf("2020-01-02 09:00:00")),
            end = GraphQLUtcTime(Timestamp.valueOf("2020-01-02 10:00:00")),
            doctors = listOf(),
            patients = listOf(),
            allDay = false
        )

    fun event(testName: String) = Event(
        title = testName,
        description = "some other description",
        startTime = Timestamp.valueOf("2020-01-02 11:00:00"),
        endTime = Timestamp.valueOf("2020-01-02 12:00:00"),
        doctors = mutableSetOf(),
        patients = mutableSetOf()
    )

    fun address(testName: String) = Address(
        type = AddressType.Other,
        address = testName,
        city = "East Vancouver",
        postalCode = "23efa",
    )

    fun user(testName: String): MediqUser {
        return MediqUser(
            uniqueId = testName,
            group = MediqGroup(name = "group from + $testName"),
            nameInfo = NameInfo(
                firstName = "Yeet",
                middleName = "Ive",
                lastName = "McSkeet"
            ),
        )
    }

    fun graphQLPatient(testName: String): PatientDto {
        return PatientDto(
            pid = PatientDto.PatientDtoId(UUID.nameUUIDFromBytes("bytes!".encodeToByteArray())),
            firstName = testName,
            middleName = "middle name!",
            lastName = "last name!",
            dateOfBirth = GraphQLDate(Timestamp.valueOf("2020-01-02 09:00:00").toLocalDateTime().toLocalDate()),
            insuranceNumber = null,
            sex = Sex.Male,
            gender = "yeet",
            ethnicity = Ethnicity.Asian,
            thumbnail = null,
        )
    }

    fun record(testName: String, patient: Patient): MediqRecord {
        return MediqRecord(
            patient = patient,
            jsonBlob = "{testName: $testName}",
            type = RecordType.Form,
            creationDate = Timestamp.valueOf("2020-01-02 09:00:00"),
        )
    }

    fun graphQLPatientRecordInput(testName: String): CreateRecordDto {
        return CreateRecordDto(
            patient = PatientDto.PatientDtoId(UUID.nameUUIDFromBytes("eb".toByteArray())),
            jsonBlob = "{testName: \"$testName\"}",
            recordType = RecordType.Blood
        )
    }

    fun form(testName: String): Survey {
        return Survey(
            name = "$testName. find out what kind of harry potter house elf you are",
            sections = setOf(
                SurveySection(
                    name = "$testName. astrological sign",
                    number = 1,
                    description = null,
                    fields = setOf(
                        SurveyFieldEntity(
                            title = "$testName. when were you born?",
                            surveyFieldType = SurveyFieldType.Date,
                            dateUpperBound = Date(Instant.now().toEpochMilli()),
                            dateLowerBound = null,
                            number = 1,
                        )
                    )
                ),
                SurveySection(
                    name = "$testName. body type",
                    number = 2,
                    description = null,
                    fields = setOf(
                        SurveyFieldEntity(
                            title = "$testName. u fat?",
                            surveyFieldType = SurveyFieldType.SingleMultiSelect,
                            multiSelectPossibilities = listOf("yes", "no"),
                            number = 1,
                        ),
                        SurveyFieldEntity(
                            title = "$testName. u tall?",
                            surveyFieldType = SurveyFieldType.SingleMultiSelect,
                            multiSelectPossibilities = listOf("yes", "no"),
                            number = 2,
                        )
                    )
                ),
                SurveySection(
                    name = "$testName. how much you like ice cream",
                    number = 3,
                    description = null,
                    fields = mutableSetOf(
                        SurveyFieldEntity(
                            title = "$testName. how long have you had this opinion on ice cream",
                            number = 1,
                            surveyFieldType = SurveyFieldType.Date,
                            dateUpperBound = Date(Instant.now().toEpochMilli()),
                            dateLowerBound = null
                        ),
                        SurveyFieldEntity(
                            title = "$testName. how long have you had this opinion on ice cream",
                            surveyFieldType = SurveyFieldType.Date,
                            dateUpperBound = Date(Instant.now().toEpochMilli()),
                            number = 2,
                            dateLowerBound = null
                        )
                    )
                ),
            )
        )
    }

    fun formField(testName: String) = SurveyFieldEntity(
        title = "$testName. what is your least favorite way to say yes",
        surveyFieldType = SurveyFieldType.SingleMultiSelect,
        multiSelectPossibilities = listOf("yes", "yee", "ye", "yeee"),
        number = 1,
    )

    fun graphQLFormInput(testName: String): CreateSurveyDto {
        return CreateSurveyDto(
            name = testName,
            sections = emptyList(),
        )
    }

    fun formData(testName: String, patient: Patient): SurveyResponse {
        return SurveyResponse(JsonObject().apply {
            addProperty("hello", testName)
        }, patient)
    }
}