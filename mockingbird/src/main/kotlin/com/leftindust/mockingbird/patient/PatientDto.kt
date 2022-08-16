package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.address.AddressDto
import com.leftindust.mockingbird.email.EmailDto
import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.Sex
import com.leftindust.mockingbird.phone.PhoneDto
import java.time.LocalDate
import java.util.UUID

data class PatientDto(
    override val id: PatientDtoId,
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val insuranceNumber: String?,
    val sex: Sex,
    val gender: String,
    val ethnicity: Ethnicity?,
) : AbstractGraphQLDto<PatientDto.PatientDtoId>() {

    companion object {
        const val GRAPHQL_TYPE = "Patient"
        const val GRAPHQL_ADDRESS_FIELD_NAME = "addresses"
        const val GRAPHQL_EMAIL_FIELD_NAME = "emails"
        const val GRAPHQL_PHONE_FIELD_NAME = "phoneNumbers"
    }


    data class PatientDtoId(override val value: UUID) : GraphQLID<UUID>


}