package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class CompleteSurveyDtoMutationController(
    private val createCompleteSurveyService: CreateCompleteSurveyService,
    private val createCompleteSurveyDtoToCreateCompleteSurveyConverter: InfallibleConverter<CreateCompleteSurveyDto, CreateCompleteSurvey>,
    private val completeSurveyToCompleteSurveyDtoConverter: InfallibleConverter<CompleteSurvey, CompleteSurveyDto>,
) {
    @MutationMapping("createCompleteSurvey")
    suspend fun createCompleteSurvey(@Argument("createCompleteSurvey") createCompleteSurveyDto: CreateCompleteSurveyDto): CompleteSurveyDto? {
        val createCompleteSurvey = createCompleteSurveyDtoToCreateCompleteSurveyConverter.convert(createCompleteSurveyDto)
        val completeSurvey = createCompleteSurveyService.createCompleteSurvey(createCompleteSurvey)
            ?: return null
        return completeSurveyToCompleteSurveyDtoConverter.convert(completeSurvey)
    }
}

