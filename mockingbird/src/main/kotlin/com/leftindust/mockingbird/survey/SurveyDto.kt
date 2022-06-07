package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import java.util.UUID

data class SurveyDto(
    override val id: SurveyDtoId,
    val name: String,
) : AbstractGraphQLDto<SurveyDto.SurveyDtoId>() {
    data class SurveyDtoId(override val value: UUID) : GraphQLID<UUID>

    suspend fun sections(): List<SurveySectionDto> = TODO()
}

