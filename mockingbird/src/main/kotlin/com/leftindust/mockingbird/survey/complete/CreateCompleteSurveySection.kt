package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionDto

interface CreateCompleteSurveySection {
    val surveyTemplateSectionId: SurveyTemplateSectionDto.SurveyTemplateSectionDtoId
    val completedSurveyInputs: List<CreateCompleteSurveyInput>
}