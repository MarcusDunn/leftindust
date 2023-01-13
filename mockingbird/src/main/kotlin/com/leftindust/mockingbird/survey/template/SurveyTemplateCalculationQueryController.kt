package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.NullSubQueryException
import dev.forkhandles.result4k.onFailure
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class SurveyTemplateCalculationQueryController(
    private val readSurveyTemplateCalculationService: ReadSurveyTemplateCalculationService,
) {
    @SchemaMapping(field = "calculations", typeName = SurveyTemplateDto.GRAPHQL_TYPE_NAME)
    fun surveyTemplateCalculations(surveyTemplateDto: SurveyTemplateDto): List<SurveyTemplateCalculationDto> {
        val surveyTemplateCalculations =
            readSurveyTemplateCalculationService.surveyTemplateCalculationBySurveyTemplateId(surveyTemplateDto.id)
                ?: throw NullSubQueryException(
                    surveyTemplateDto,
                    ReadSurveyTemplateCalculationService::surveyTemplateCalculationBySurveyTemplateId
                )
        return surveyTemplateCalculations.map {
            it.toSurveyTemplateCalculationDto().onFailure { throw it.reason.toMockingbirdException() }
        }
    }
}