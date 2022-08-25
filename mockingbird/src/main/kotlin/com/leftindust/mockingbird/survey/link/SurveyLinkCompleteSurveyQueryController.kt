package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.survey.complete.CompleteSurveyDto
import com.leftindust.mockingbird.survey.complete.CompleteSurveyToCompleteSurveyDtoConverter
import com.leftindust.mockingbird.survey.complete.ReadCompleteSurveyService
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class SurveyLinkCompleteSurveyQueryController(
    private val readCompletedSurveyService: ReadCompleteSurveyService,
    private val completeSurveyToCompleteSurveyDtoConverter: CompleteSurveyToCompleteSurveyDtoConverter,
) {

    @SchemaMapping(field = "completedSurveys", typeName = SurveyLinkDto.GRAPHQL_TYPE)
    suspend fun completedSurveysBySurveyLink(surveyLinkDto: SurveyLinkDto): List<CompleteSurveyDto> {
        val completeSurveys = readCompletedSurveyService.getBySurveyLink(surveyLinkDto.id)
            ?: throw NullSubQueryException(surveyLinkDto, ReadCompleteSurveyService::getBySurveyLink)
        return completeSurveys.map { completeSurveyToCompleteSurveyDtoConverter.convert(it) }
    }
}