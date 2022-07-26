package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.survey.template.SurveyTemplateDto.SurveyTemplateDtoId
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_SURVEY_TEMPLATE_CALCULATION')")
interface ReadSurveyTemplateCalculationService {
    fun surveyTemplateCalculationBySurveyTemplateId(surveyTemplateDtoId: SurveyTemplateDtoId): List<SurveyTemplateCalculation>?
}
