package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.survey.template.SurveyTemplateDto
import com.leftindust.mockingbird.survey.template.toSurveyTemplateDto
import dev.forkhandles.result4k.onFailure
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class SurveyLinkSurveyTemplateQueryController(
    private val readSurveyTemplateBySurveyLinkService: ReadSurveyTemplateBySurveyLinkService,
) {
    @SchemaMapping(field = "surveyTemplate", typeName = SurveyLinkDto.GRAPHQL_TYPE)
    suspend fun surveyLinkSurveyTemplate(surveyLinkDto: SurveyLinkDto): SurveyTemplateDto {
        val surveyTemplate = readSurveyTemplateBySurveyLinkService.getBySurveyLinkId(surveyLinkDto.id)
            ?: throw NullSubQueryException(surveyLinkDto, ReadSurveyTemplateBySurveyLinkService::getBySurveyLinkId)
        return surveyTemplate.toSurveyTemplateDto().onFailure { throw it.reason.toMockingbirdException() }
    }
}