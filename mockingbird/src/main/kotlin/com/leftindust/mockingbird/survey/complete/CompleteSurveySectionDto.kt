package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import com.leftindust.mockingbird.survey.complete.CompleteSurveySectionDto.CompleteSurveySectionDtoId
import java.util.UUID

data class CompleteSurveySectionDto(
    override val id: CompleteSurveySectionDtoId,
    val inputs: List<CompleteSurveySectionInputDto>
) : AbstractGraphQLDto<CompleteSurveySectionDtoId>() {
    companion object {
        const val GRAPHQL_TYPE = "CompleteSurveySection"
    }

    data class CompleteSurveySectionDtoId(override val value: UUID) : GraphQLID<UUID>
}