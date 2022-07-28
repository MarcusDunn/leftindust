package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionInputDto

interface CreateCompleteSurveyInput {
    val surveyTemplateSectionInputId: SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId
    val value: String
}