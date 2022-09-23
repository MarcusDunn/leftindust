package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.survey.template.SurveyTemplate
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_SURVEY_TEMPLATE')")
interface ReadSurveyTemplateBySurveyLinkService {
    suspend fun getBySurveyLinkId(surveyLinkDtoId: SurveyLinkDto.SurveyLinkDtoId): SurveyTemplate?
}