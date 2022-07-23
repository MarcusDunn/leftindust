package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import com.leftindust.mockingbird.survey.SurveyTemplateCalculationDto.SurveyTemplateCalculationDtoId
import java.util.UUID

data class SurveyTemplateCalculationDto(
    override val id: SurveyTemplateCalculationDtoId,
    // todo add more fields here
) : AbstractGraphQLDto<SurveyTemplateCalculationDtoId>() {
    data class SurveyTemplateCalculationDtoId(override val value: UUID) : GraphQLID<UUID>
}

