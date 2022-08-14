package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionInputDto

data class CreateCompleteSurveyInputDto(
    override val surveyTemplateSectionInputId: SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId,
    override val value: String,
): CreateCompleteSurveyInput