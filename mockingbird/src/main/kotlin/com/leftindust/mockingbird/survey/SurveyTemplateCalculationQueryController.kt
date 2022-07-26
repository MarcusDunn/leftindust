package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class SurveyTemplateCalculationQueryController(
    private val readSurveyTemplateCalculationService: ReadSurveyTemplateCalculationService,
    private val surveyTemplateCalculationToSurveyTemplateCalculationDtoConverter: InfallibleConverter<SurveyTemplateCalculation, SurveyTemplateCalculationDto>
){
    @SchemaMapping(field = "calculations", typeName = SurveyTemplateDto.GRAPHQL_TYPE_NAME)
    fun surveyTemplateCalculations(surveyTemplateDto: SurveyTemplateDto): List<SurveyTemplateCalculationDto> {
        val surveyTemplateCalculations = readSurveyTemplateCalculationService.surveyTemplateCalculationBySurveyTemplateId(surveyTemplateDto.id)
            ?: throw NullSubQueryException(surveyTemplateDto, ReadSurveyTemplateCalculationService::surveyTemplateCalculationBySurveyTemplateId)
        return surveyTemplateCalculations.map { surveyTemplateCalculationToSurveyTemplateCalculationDtoConverter.convert(it) }
    }
}