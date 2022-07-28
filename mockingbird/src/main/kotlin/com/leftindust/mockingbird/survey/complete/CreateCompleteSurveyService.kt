package com.leftindust.mockingbird.survey.complete

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('CREATE_COMPLETE_SURVEY')")
interface CreateCompleteSurveyService {
    suspend fun createCompleteSurvey(createCompleteSurvey: CreateCompleteSurvey): CompleteSurvey
}
