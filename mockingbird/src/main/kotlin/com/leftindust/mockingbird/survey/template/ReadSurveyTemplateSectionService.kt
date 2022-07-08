package com.leftindust.mockingbird.survey.template

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_SURVEY_TEMPLATE')")
interface ReadSurveyTemplateSectionService {
    suspend fun getSurveyTemplateSectionsBySurveyTemplateId(surveyTemplateId: SurveyTemplateDto.SurveyTemplateDtoId): List<SurveyTemplateSection>
}

