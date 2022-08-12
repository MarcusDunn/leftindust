package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.address.AddressDto
import com.leftindust.mockingbird.email.EmailDto
import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import com.leftindust.mockingbird.phone.PhoneDto
import java.time.LocalDate
import java.util.UUID

data class DoctorDto(
    override val id: DoctorDtoId,
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val phoneNumbers: List<PhoneDto>,
    val addresses: List<AddressDto>,
    val emails: List<EmailDto>?,
    val thumbnail: ByteArray?,
    val title: String?,
    val dateOfBirth: LocalDate?,
) : AbstractGraphQLDto<DoctorDto.DoctorDtoId>() {
    companion object {
        const val GRAPHQL_TYPE = "Doctor"
    }
    data class DoctorDtoId(override val value: UUID) : GraphQLID<UUID>
}