package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.survey.complete.CompleteSurveyDto.CompleteSurveyDtoId

interface ReadCompleteSurveySectionService {
    fun completeSurveySectionsByCompleteSurveyId(completeSurveyId: CompleteSurveyDtoId): List<CompleteSurveySection>?
}
