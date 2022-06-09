package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import mu.KotlinLogging
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Component

@Component
class SurveyQueryController(
    private val readSurveyService: ReadSurveyService,
    private val surveyToSurveyDtoConverter: InfallibleConverter<Survey, SurveyDto>,
) {
    private val logger = KotlinLogging.logger { }

    @QueryMapping
    private suspend fun surveysById(@Argument surveyIds: List<SurveyDto.SurveyDtoId>): List<SurveyDto?> {
        return surveyIds
            .map { readSurveyService.getBySurveyId(it) }
            .map { it?.let { survey -> surveyToSurveyDtoConverter.convert(survey) } }
    }
}