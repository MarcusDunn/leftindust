package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionDto

data class CreateCompleteSurveySectionDto(
    override val surveyTemplateSectionId: SurveyTemplateSectionDto.SurveyTemplateSectionDtoId,
    override val completedSurveyInputs: List<CreateCompleteSurveyInputDto>,
): CreateCompleteSurveySection