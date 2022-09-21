package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import java.util.UUID

data class CompleteSurveyDto(
    override val id: CompleteSurveyDtoId,
    val sections: List<CompleteSurveySectionDto>
): AbstractGraphQLDto<CompleteSurveyDto.CompleteSurveyDtoId>() {
    companion object {
        const val GRAPHQL_TYPE = "CompleteSurvey"
    }
    data class CompleteSurveyDtoId(override val value: UUID): GraphQLID<UUID>
}