package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.survey.template.SurveyTemplateDto.SurveyTemplateDtoId
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class SurveyTemplateQueryController(
    private val readSurveyTemplateService: ReadSurveyTemplateService,
    private val surveyTemplateToSurveyTemplateDtoConverter: InfallibleConverter<SurveyTemplate, SurveyTemplateDto>,
) {

    @QueryMapping("surveyTemplateById")
    suspend fun surveyTemplateBySurveyTemplateId(@Argument surveyTemplateId: SurveyTemplateDtoId): SurveyTemplateDto? {
        val surveyTemplate = readSurveyTemplateService.getByTemplateSurveyId(surveyTemplateId)
            ?: return null
        return surveyTemplateToSurveyTemplateDtoConverter.convert(surveyTemplate)
    }

    @QueryMapping("surveyTemplateByRange")
    suspend fun surveyTemplateByRange(@Argument("range") rangeDto: RangeDto): List<SurveyTemplateDto> {
        val surveyTemplates = readSurveyTemplateService.getByRange(rangeDto)
        return surveyTemplates.map {  surveyTemplateToSurveyTemplateDtoConverter.convert(it) }
    }
}