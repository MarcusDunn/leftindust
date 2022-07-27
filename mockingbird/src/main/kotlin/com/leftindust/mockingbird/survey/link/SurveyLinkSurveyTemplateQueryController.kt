package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.survey.template.SurveyTemplate
import com.leftindust.mockingbird.survey.template.SurveyTemplateDto
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class SurveyLinkSurveyTemplateQueryController(
    private val readSurveyTemplateBySurveyLinkService: ReadSurveyTemplateBySurveyLinkService,
    private val surveyTemplateToSurveyTemplateDtoInfallibleConverter: InfallibleConverter<SurveyTemplate, SurveyTemplateDto>,
    ) {
    @SchemaMapping(field = "surveyTemplate", typeName = SurveyLinkDto.GRAPHQL_TYPE)
    suspend fun surveyLinkSurveyTemplate(surveyLinkDto: SurveyLinkDto): SurveyTemplateDto {
        val surveyTemplate = readSurveyTemplateBySurveyLinkService.getBySurveyLinkId(surveyLinkDto.id)
            ?: throw NullSubQueryException(surveyLinkDto, ReadSurveyTemplateBySurveyLinkService::getBySurveyLinkId)
        return surveyTemplateToSurveyTemplateDtoInfallibleConverter.convert(surveyTemplate)
    }
}