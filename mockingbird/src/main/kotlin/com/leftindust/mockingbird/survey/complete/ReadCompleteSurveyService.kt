package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.survey.complete.CompleteSurveyDto.CompleteSurveyDtoId

interface ReadCompleteSurveyService {
    suspend fun completeSurveyByCompleteSurveyId(completeSurveyId: CompleteSurveyDtoId): CompleteSurvey?
}
