package com.leftindust.mockingbird.survey

import kotlinx.coroutines.flow.Flow
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_SURVEY')")
interface ReadSurveySectionService {
    suspend fun getBySurveyId(surveyId: SurveyDto.SurveyDtoId): Set<SurveySection>?
}
