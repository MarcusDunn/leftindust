package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.survey.complete.CompleteSurveyDto.CompleteSurveyDtoId
import com.leftindust.mockingbird.survey.link.SurveyLinkDto

interface ReadCompleteSurveyService {
    suspend fun completeSurveyByCompleteSurveyId(completeSurveyId: CompleteSurveyDtoId): CompleteSurvey?
    suspend fun getBySurveyLink(surveyLinkDtoId: SurveyLinkDto.SurveyLinkDtoId): List<CompleteSurvey>?
}
