package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.survey.template.SurveyTemplateDto

interface CreateCompleteSurvey {
    val surveyTemplateId: SurveyTemplateDto.SurveyTemplateDtoId
    val completeSurveyTemplateSections: List<CreateCompleteSurveySection>
}
