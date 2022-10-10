package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.MockingbirdException
import dev.forkhandles.result4k.Result4k
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('CREATE_COMPLETE_SURVEY')")
interface CreateCompleteSurveyService {
    suspend fun createCompleteSurvey(createCompleteSurvey: CreateCompleteSurvey): Result4k<CompleteSurvey, MockingbirdException>
}
