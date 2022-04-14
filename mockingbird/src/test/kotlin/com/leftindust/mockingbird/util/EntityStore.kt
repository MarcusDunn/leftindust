package com.leftindust.mockingbird.util

import com.google.gson.JsonObject
import com.leftindust.mockingbird.dao.entity.*
import com.leftindust.mockingbird.dao.entity.enums.Ethnicity
import com.leftindust.mockingbird.dao.entity.enums.RecordType
import com.leftindust.mockingbird.dao.entity.enums.Relationship
import com.leftindust.mockingbird.dao.entity.enums.Sex
import com.leftindust.mockingbird.extensions.gqlID
import com.leftindust.mockingbird.graphql.types.*
import com.leftindust.mockingbird.graphql.types.input.*
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
                type = GraphQLAddressType.Home,
                city = "North Vancouver",
                countryState = CountryState(
                    country = GraphQLCountry.Canada,
                    province = GraphQLCanadianProvince.Provinces.Alberta.name
                ),
                address = "874 West 1st Street",
                postalCode = "y7h1p4",
            )
        ),
        emails = setOf(Email(email = "hello@world.ca", type = GraphQLEmailType.Personal)),
        phones = setOf(Phone("6632231111", GraphQLPhoneType.Home)),
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
                type = GraphQLAddressType.Home,
                address = "999 East 7th Drive",
                city = "West Vancouver",
                countryState = CountryState(
                    country = GraphQLCountry.Canada,
                    province = GraphQLCanadianProvince.Provinces.Alberta.name
                ),
                postalCode = "y7h1p5",
            )
        ),
        emails = setOf(Email(email = "world@hello.ca", type = GraphQLEmailType.Personal)),
        phones = setOf(Phone("6632231211", GraphQLPhoneType.Home)),
        title = "sir",
        patients = mutableSetOf(),
        schedule = emptySet(),
    )

    fun graphQLPatientInput(testName: String) = GraphQLPatientInput(
        nameInfo = GraphQLNameInfoInput(
            firstName = "aydan",
            middleName = testName,
            lastName = "gaite",
        ),
        phones = listOf(
            GraphQLPhoneInput(
                number = "11111111",
                type = GraphQLPhoneType.Work,
            )
        ),
        dateOfBirth = GraphQLDateInput(
            day = 12,
            month = GraphQLMonth.Apr,
            year = 1948
        ),
        addresses = listOf(
            GraphQLAddressInput(
                addressType = GraphQLAddressType.Home,
                address = "6732 main st",
                city = "East Vancouver",
                country = GraphQLCountry.Canada,
                province = GraphQLCanadianProvince.Provinces.NewBrunswick.name,
                postalCode = "h221234",
            )
        ),
        emails = listOf(
            GraphQLEmailInput(
                type = GraphQLEmailType.School,
                email = "hello@mars.ca",
            )
        ),
        insuranceNumber = gqlID(111111111),
        sex = Sex.Male,
        ethnicity = Ethnicity.AmericanAboriginal,
        emergencyContacts = listOf(
            GraphQLEmergencyContactInput(
                firstName = "mom firstName",
                middleName = "mom middleName",
                lastName = "mom lastName",
                relationship = Relationship.Parent,
                phones = listOf(
                    GraphQLPhoneInput(
                        number = "111111111",
                        type = GraphQLPhoneType.Work,
                    ),
                    GraphQLPhoneInput(
                        number = "223223222",
                        type = GraphQLPhoneType.Home,
                    ),
                ),
                emails = listOf(
                    GraphQLEmailInput(
                        type = GraphQLEmailType.School,
                        email = "bye@saturn.uk",
                    )
                )
            )
        ),
    )

    fun graphQLEventInput(testName: String) =
        GraphQLEventInput(
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
        type = GraphQLAddressType.Other,
        address = testName,
        city = "East Vancouver",
        countryState = CountryState(
            country = GraphQLCountry.Canada,
            province = GraphQLCanadianProvince.Provinces.Yukon.name
        ),
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

    fun graphQLPatient(testName: String): GraphQLPatient {
        return GraphQLPatient(
            pid = GraphQLPatient.ID(UUID.nameUUIDFromBytes("bytes!".encodeToByteArray())),
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

    fun graphQLRecordInput(testName: String): GraphQLRecordInput {
        return GraphQLRecordInput(
            patient = GraphQLPatient.ID(UUID.nameUUIDFromBytes("eb".toByteArray())),
            jsonBlob = "{testName: \"$testName\"}",
            recordType = RecordType.Blood
        )
    }

    fun form(testName: String): Form {
        return Form(
            name = "$testName. find out what kind of harry potter house elf you are",
            sections = setOf(
                FormSection(
                    name = "$testName. astrological sign",
                    number = 1,
                    description = null,
                    fields = setOf(
                        FormField(
                            title = "$testName. when were you born?",
                            dataType = DataType.Date,
                            dateUpperBound = Date(Instant.now().toEpochMilli()),
                            dateLowerBound = null,
                            number = 1,
                        )
                    )
                ),
                FormSection(
                    name = "$testName. body type",
                    number = 2,
                    description = null,
                    fields = setOf(
                        FormField(
                            title = "$testName. u fat?",
                            dataType = DataType.SingleMultiSelect,
                            multiSelectPossibilities = listOf("yes", "no"),
                            number = 1,
                        ),
                        FormField(
                            title = "$testName. u tall?",
                            dataType = DataType.SingleMultiSelect,
                            multiSelectPossibilities = listOf("yes", "no"),
                            number = 2,
                        )
                    )
                ),
                FormSection(
                    name = "$testName. how much you like ice cream",
                    number = 3,
                    description = null,
                    fields = mutableSetOf(
                        FormField(
                            title = "$testName. how long have you had this opinion on ice cream",
                            number = 1,
                            dataType = DataType.Date,
                            dateUpperBound = Date(Instant.now().toEpochMilli()),
                            dateLowerBound = null
                        ),
                        FormField(
                            title = "$testName. how long have you had this opinion on ice cream",
                            dataType = DataType.Date,
                            dateUpperBound = Date(Instant.now().toEpochMilli()),
                            number = 2,
                            dateLowerBound = null
                        )
                    )
                ),
            )
        )
    }

    fun formField(testName: String) = FormField(
        title = "$testName. what is your least favorite way to say yes",
        dataType = DataType.SingleMultiSelect,
        multiSelectPossibilities = listOf("yes", "yee", "ye", "yeee"),
        number = 1,
    )

    fun graphQLFormInput(testName: String): GraphQLFormTemplateInput {
        return GraphQLFormTemplateInput(
            name = testName,
            sections = emptyList(),
        )
    }

    fun formData(testName: String, patient: Patient): FormData {
        return FormData(JsonObject().apply {
            addProperty("hello", testName)
        }, patient)
    }
}