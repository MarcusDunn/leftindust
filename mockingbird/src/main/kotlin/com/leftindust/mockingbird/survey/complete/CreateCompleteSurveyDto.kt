package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.survey.link.SurveyLinkDto

data class CreateCompleteSurveyDto(
    val surveyLinkId: SurveyLinkDto.SurveyLinkDtoId,
    val completeSurveyTemplateSections: List<CreateCompleteSurveySectionDto>,
)

