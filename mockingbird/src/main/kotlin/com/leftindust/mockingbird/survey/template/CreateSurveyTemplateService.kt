package com.leftindust.mockingbird.survey.template

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('CREATE_SURVEY')")
interface CreateSurveyTemplateService {
    suspend fun createSurveyTemplate(surveyTemplate: CreateSurveyTemplate): SurveyTemplate
}
