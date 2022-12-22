package com.leftindust.mockingbird.patient


import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.Sex
import java.time.LocalDate
import java.util.UUID

data class PatientDto(
    override val id: PatientDtoId,
    val dateOfBirth: LocalDate,
    val insuranceNumber: String?,
    val sex: Sex,
    val gender: String?,
    val ethnicity: Ethnicity?,
) : AbstractGraphQLDto<PatientDto.PatientDtoId>() {

    companion object {
        const val GRAPHQL_TYPE = "Patient"
    }


    data class PatientDtoId(override val value: UUID) : GraphQLID<UUID>
}

fun Patient.toPatientDto(): PatientDto {
    return PatientDto(
        PatientDto.PatientDtoId(id),
        dateOfBirth, insuranceNumber, sex, gender, ethnicity
    )
}
