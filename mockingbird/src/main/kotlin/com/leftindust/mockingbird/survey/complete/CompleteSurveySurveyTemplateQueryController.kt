package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.survey.template.ReadSurveyTemplateService
import com.leftindust.mockingbird.survey.template.SurveyTemplate
import com.leftindust.mockingbird.survey.template.SurveyTemplateDto
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class CompleteSurveySurveyTemplateQueryController(
    private val readSurveyTemplateService: ReadSurveyTemplateService,
    private val surveyTemplateToSurveyTemplateDtoConverter: InfallibleConverter<SurveyTemplate, SurveyTemplateDto>
) {
    @SchemaMapping(field = "surveyTemplate", typeName = CompleteSurveyDto.GRAPHQL_TYPE)
    suspend fun surveyTemplateByCompleteSurvey(completeSurveyDto: CompleteSurveyDto): SurveyTemplateDto {
        val completedSurvey = readSurveyTemplateService.getByCompletedSurvey(completeSurveyDto.id)
            ?: throw NullSubQueryException(completeSurveyDto, ReadSurveyTemplateService::getByCompletedSurvey)
        return surveyTemplateToSurveyTemplateDtoConverter.convert(completedSurvey)
    }
}