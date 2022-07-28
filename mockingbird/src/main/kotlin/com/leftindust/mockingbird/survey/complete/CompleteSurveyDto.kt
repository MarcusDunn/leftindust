package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import java.util.UUID

data class CompleteSurveyDto(
    override val id: CompleteSurveyDtoId
): AbstractGraphQLDto<CompleteSurveyDto.CompleteSurveyDtoId>() {
    data class CompleteSurveyDtoId(override val value: UUID): GraphQLID<UUID>
}