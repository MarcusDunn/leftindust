package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.survey.template.SurveyTemplateDto.SurveyTemplateDtoId
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_SURVEY_TEMPLATE_SECTION')")
interface ReadSurveyTemplateSectionService {
    suspend fun surveyTemplateSectionServiceBySurveySectionId(surveyTemplateId: SurveyTemplateDtoId): List<SurveyTemplateSection>?
}