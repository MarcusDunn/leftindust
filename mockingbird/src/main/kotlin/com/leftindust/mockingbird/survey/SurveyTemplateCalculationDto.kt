package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import com.leftindust.mockingbird.survey.SurveyTemplateCalculationDto.SurveyTemplateCalculationDtoId
import java.util.UUID

data class SurveyTemplateCalculationDto(
    override val id: SurveyTemplateCalculationDtoId,
    val index: Int,
    val label: String,
    val inputType: SurveyTemplateInputType,
    val showOnComplete: Boolean,
    val calculation: String,
) : AbstractGraphQLDto<SurveyTemplateCalculationDtoId>() {
    data class SurveyTemplateCalculationDtoId(override val value: UUID) : GraphQLID<UUID>
}

