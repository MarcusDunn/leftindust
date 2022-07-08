package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller


@Controller
class SurveyTemplateQueryController(
    private val readSurveyTemplateService: ReadSurveyTemplateService,
    private val surveyTemplateToSurveyTemplateDtoConverter: InfallibleConverter<SurveyTemplate, SurveyTemplateDto>
) {
    @QueryMapping
    suspend fun surveyTemplateBySurveyTemplateId(@Argument surveyTemplateId: SurveyTemplateDto.SurveyTemplateDtoId): SurveyTemplateDto? {
        val surveyTemplate = readSurveyTemplateService.getSurveyTemplateBySurveyId(surveyTemplateId)
        return if (surveyTemplate != null) {
            surveyTemplateToSurveyTemplateDtoConverter.convert(surveyTemplate)
        } else {
            null
        }
    }
}