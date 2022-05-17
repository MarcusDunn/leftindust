package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import java.util.UUID


data class SurveySectionDto(
    override val id: SurveySectionDtoId,
    val name: String,
    val number: Int,
    val description: String?,
) : AbstractGraphQLDto<SurveySectionDto.SurveySectionDtoId>() {
    data class SurveySectionDtoId(override val value: UUID) : GraphQLID<UUID>


}