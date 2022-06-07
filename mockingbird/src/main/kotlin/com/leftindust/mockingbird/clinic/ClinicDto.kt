package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import java.util.UUID

data class ClinicDto(val name: String, override val id: ClinicDtoId) : AbstractGraphQLDto<ClinicDto.ClinicDtoId>() {
    data class ClinicDtoId(override val value: UUID) : GraphQLID<UUID>
}