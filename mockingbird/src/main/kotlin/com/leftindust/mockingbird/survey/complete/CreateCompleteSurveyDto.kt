package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.survey.template.SurveyTemplateDto

data class CreateCompleteSurveyDto(
    override val surveyTemplateId: SurveyTemplateDto.SurveyTemplateDtoId,
    override val completeSurveyTemplateSections: List<CreateCompleteSurveySectionDto>,
): CreateCompleteSurvey

