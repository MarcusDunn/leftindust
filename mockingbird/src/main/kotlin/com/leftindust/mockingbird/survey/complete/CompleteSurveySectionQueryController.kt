package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.NullSubQueryException
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class CompleteSurveySectionQueryController(
    private val readCompleteSurveySectionService: ReadCompleteSurveySectionService,
    private val completeSurveySectionToCompleteSurveySectionDtoConverter: CompleteSurveySectionToCompleteSurveySectionDtoConverter,
) {

    @SchemaMapping(field = "sections", typeName = CompleteSurveyDto.GRAPHQL_TYPE)
    fun sectionsByCompleteSurvey(completeSurveyDto: CompleteSurveyDto): List<CompleteSurveySectionDto> {
        val completeSurveySections = readCompleteSurveySectionService.completeSurveySectionsByCompleteSurveyId(completeSurveyDto.id)
            ?: throw NullSubQueryException(completeSurveyDto, ReadCompleteSurveySectionService::completeSurveySectionsByCompleteSurveyId)
        return completeSurveySections.map { completeSurveySectionToCompleteSurveySectionDtoConverter.convert(it) }
    }
}

