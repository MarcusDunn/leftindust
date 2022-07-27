package com.leftindust.mockingbird.survey.link

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_SURVEY_LINK')")
interface ReadSurveyLinkService {
    suspend fun surveyLinkBySurveyLinkId(surveyLinkId: SurveyLinkDto.SurveyLinkDtoId): SurveyLink?
}
