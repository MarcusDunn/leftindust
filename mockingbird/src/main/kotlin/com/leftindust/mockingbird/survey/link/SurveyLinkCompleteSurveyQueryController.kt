package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.survey.complete.CompleteSurveyDto
import com.leftindust.mockingbird.survey.complete.ReadCompleteSurveyService
import com.leftindust.mockingbird.survey.complete.toCompleteSurveyDto
import dev.forkhandles.result4k.onFailure
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class SurveyLinkCompleteSurveyQueryController(
    private val readCompletedSurveyService: ReadCompleteSurveyService,
) {
    @SchemaMapping(field = "completedSurvey", typeName = SurveyLinkDto.GRAPHQL_TYPE)
    suspend fun completedSurveyBySurveyLink(surveyLinkDto: SurveyLinkDto): CompleteSurveyDto? {
        val completeSurveys = readCompletedSurveyService.getBySurveyLink(surveyLinkDto.id)
            ?: return null
        return completeSurveys.toCompleteSurveyDto().onFailure { throw it.reason.toMockingbirdException() }
    }
}