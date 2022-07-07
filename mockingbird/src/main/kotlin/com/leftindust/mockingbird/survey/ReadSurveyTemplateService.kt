package com.leftindust.mockingbird.survey

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_SURVEY_TEMPLATE')")
interface ReadSurveyTemplateService {
    fun getSurveySections(surveyTemplateId: SurveyTemplateDto.SurveyTemplateDtoId): List<SurveyTemplateSection>
}
