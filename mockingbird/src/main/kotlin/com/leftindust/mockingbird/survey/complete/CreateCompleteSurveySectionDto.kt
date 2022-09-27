package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionDto

data class CreateCompleteSurveySectionDto(
    val surveyTemplateSectionId: SurveyTemplateSectionDto.SurveyTemplateSectionDtoId,
    val completedSurveyInputs: List<CreateCompleteSurveyInputDto>,
)