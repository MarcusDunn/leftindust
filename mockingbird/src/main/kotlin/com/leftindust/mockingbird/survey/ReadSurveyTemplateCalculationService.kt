package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.survey.SurveyTemplateDto.SurveyTemplateDtoId
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_SURVEY_TEMPLATE_CALCULATION')")
interface ReadSurveyTemplateCalculationService {
    fun surveyTemplateCalculationBySurveyTemplateId(surveyTemplateDtoId: SurveyTemplateDtoId): List<SurveyTemplateCalculation>?
}
