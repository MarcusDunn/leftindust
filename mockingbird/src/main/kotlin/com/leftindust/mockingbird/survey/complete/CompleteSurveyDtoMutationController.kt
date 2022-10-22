package com.leftindust.mockingbird.survey.complete

import dev.forkhandles.result4k.onFailure
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class CompleteSurveyDtoMutationController(
    private val createCompleteSurveyService: CreateCompleteSurveyService,
) {
    @MutationMapping("createCompleteSurvey")
    suspend fun createCompleteSurvey(@Argument("createCompleteSurvey") createCompleteSurveyDto: CreateCompleteSurveyDto): CompleteSurveyDto? {
        val createCompleteSurvey = createCompleteSurveyDto.toCreateCompleteSurvey().onFailure { throw it.reason.toMockingbirdException() }
        val completeSurvey = createCompleteSurveyService.createCompleteSurvey(createCompleteSurvey).onFailure { throw it.reason.toMockingbirdException() }
        return completeSurvey.toCompleteSurveyDto()
    }
}

