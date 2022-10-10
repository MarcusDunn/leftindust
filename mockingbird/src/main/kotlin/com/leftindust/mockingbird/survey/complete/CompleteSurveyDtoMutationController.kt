package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.InfallibleConverter
import dev.forkhandles.result4k.onFailure
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class CompleteSurveyDtoMutationController(
    private val createCompleteSurveyService: CreateCompleteSurveyService,
    private val completeSurveyToCompleteSurveyDtoConverter: InfallibleConverter<CompleteSurvey, CompleteSurveyDto>,
) {
    @MutationMapping("createCompleteSurvey")
    suspend fun createCompleteSurvey(@Argument("createCompleteSurvey") createCompleteSurveyDto: CreateCompleteSurveyDto): CompleteSurveyDto? {
        val createCompleteSurvey = createCompleteSurveyDto.toCreateCompleteSurvey().onFailure { throw it.reason.toException() }
        val completeSurvey = createCompleteSurveyService.createCompleteSurvey(createCompleteSurvey )
            ?: return null
        return completeSurveyToCompleteSurveyDtoConverter.convert(completeSurvey)
    }
}

