package com.leftindust.mockingbird.survey.link

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('CREATE_SURVEY_LINK')")
interface CreateSurveyLinkService {
    suspend fun createSurveyLink(createSurveyLink: CreateSurveyLink): SurveyLink?
}
