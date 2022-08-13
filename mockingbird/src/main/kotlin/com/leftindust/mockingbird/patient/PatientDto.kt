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
    val addresses: List<AddressDto>,
    val emails: List<EmailDto>,
    val phoneNumbers: List<PhoneDto>,
    val thumbnail: ByteArray?,
    val dateOfBirth: LocalDate,
    val insuranceNumber: String?,
    val sex: Sex,
    val gender: String,
    val ethnicity: Ethnicity?,
) : AbstractGraphQLDto<PatientDto.PatientDtoId>() {
    data class PatientDtoId(override val value: UUID) : GraphQLID<UUID>


}