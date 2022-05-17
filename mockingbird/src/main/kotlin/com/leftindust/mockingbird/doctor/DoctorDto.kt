package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import com.leftindust.mockingbird.graphql.types.*
import java.util.*

data class DoctorDto(
    override val id: DoctorDtoId,
    val firstName: String,
    val middleName: String? = null,
    val lastName: String,
    val thumbnail: String?,
    val title: String? = null,
    val dateOfBirth: LocalDateDto? = null,
) : AbstractGraphQLDto<DoctorDto.DoctorDtoId>() {
    data class DoctorDtoId(override val value: UUID) : GraphQLID<UUID>
}