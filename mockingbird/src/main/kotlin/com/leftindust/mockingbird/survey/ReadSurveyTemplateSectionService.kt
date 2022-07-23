package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.survey.SurveyTemplateDto.SurveyTemplateDtoId
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_SURVEY_TEMPLATE_SECTION')")
interface ReadSurveyTemplateSectionService {
    suspend fun surveyTemplateSectionServiceBySurveySectionId(surveyTemplateId: SurveyTemplateDtoId): List<SurveyTemplateSection>?
}