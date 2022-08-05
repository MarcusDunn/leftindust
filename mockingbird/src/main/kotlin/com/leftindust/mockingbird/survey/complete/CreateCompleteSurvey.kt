package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.survey.link.SurveyLinkDto

interface CreateCompleteSurvey {
    val surveyLinkId: SurveyLinkDto.SurveyLinkDtoId
    val completeSurveyTemplateSections: List<CreateCompleteSurveySection>
}
