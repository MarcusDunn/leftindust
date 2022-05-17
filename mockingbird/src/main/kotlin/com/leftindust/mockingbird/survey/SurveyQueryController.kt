package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.extensions.doThenNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
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
    private suspend fun surveysById(@Argument surveyIds: Flow<SurveyDto.SurveyDtoId>): Flow<SurveyDto?> {
        return surveyIds
            .map { it to readSurveyService.getBySurveyId(it) }
            .map {
                it.second?.let { surveyToSurveyDtoConverter.convert(it) }
                    ?: doThenNull { logger.debug { "returned a null element from surveysById for ${it.first}" } }
            }
    }
}