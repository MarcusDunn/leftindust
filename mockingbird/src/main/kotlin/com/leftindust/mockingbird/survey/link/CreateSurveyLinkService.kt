package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.survey.template.SurveyTemplateDto.SurveyTemplateDtoId
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('CREATE_SURVEY_LINK')")
interface CreateSurveyLinkService {
    suspend fun createSurveyLinkFromSurveyTemplateId(surveyTemplateDtoId: SurveyTemplateDtoId): SurveyLink?
}
