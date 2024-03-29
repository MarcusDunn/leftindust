package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import java.time.LocalDate
import java.util.UUID

data class DoctorDto(
    override val id: DoctorDtoId,
    val thumbnail: ByteArray?,
    val title: String?,
    val dateOfBirth: LocalDate?,
) : AbstractGraphQLDto<DoctorDto.DoctorDtoId>() {
    companion object {
        const val GRAPHQL_TYPE = "Doctor"
    }
    data class DoctorDtoId(override val value: UUID) : GraphQLID<UUID>
}