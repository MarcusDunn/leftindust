package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.survey.link.SurveyLinkDto

data class CreateCompleteSurveyDto(
    override val surveyLinkId: SurveyLinkDto.SurveyLinkDtoId,
    override val completeSurveyTemplateSections: List<CreateCompleteSurveySectionDto>,
): CreateCompleteSurvey

