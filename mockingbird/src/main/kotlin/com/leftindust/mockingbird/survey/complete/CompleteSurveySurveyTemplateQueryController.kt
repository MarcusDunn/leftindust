package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.survey.template.ReadSurveyTemplateService
import com.leftindust.mockingbird.survey.template.SurveyTemplateDto
import com.leftindust.mockingbird.survey.template.toSurveyTemplateDto
import dev.forkhandles.result4k.onFailure
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class CompleteSurveySurveyTemplateQueryController(
    private val readSurveyTemplateService: ReadSurveyTemplateService,
) {
    @SchemaMapping(field = "surveyTemplate", typeName = CompleteSurveyDto.GRAPHQL_TYPE)
    suspend fun surveyTemplateByCompleteSurvey(completeSurveyDto: CompleteSurveyDto): SurveyTemplateDto {
        val completedSurvey = readSurveyTemplateService.getByCompletedSurvey(completeSurveyDto.id)
            ?: throw NullSubQueryException(completeSurveyDto, ReadSurveyTemplateService::getByCompletedSurvey)
        return completedSurvey.toSurveyTemplateDto().onFailure { throw it.reason.toMockingbirdException() }
    }
}