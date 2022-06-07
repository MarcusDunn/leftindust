package com.leftindust.mockingbird.survey

import org.springframework.security.access.prepost.PreAuthorize


@PreAuthorize("hasAuthority('CREATE_SURVEY')")
interface CreateSurveyService {
    suspend fun createSurvey(form: CreateSurvey): Survey
}