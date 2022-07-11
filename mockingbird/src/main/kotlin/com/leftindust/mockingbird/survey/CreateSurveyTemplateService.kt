package com.leftindust.mockingbird.survey

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('CREATE_SURVEY')")
interface CreateSurveyTemplateService {
    suspend fun createSurveyTemplate(surveyTemplate: CreateSurveyTemplate): SurveyTemplate
}
