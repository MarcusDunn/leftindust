package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class SurveySectionQueryController(
    private val readSurveySectionService: ReadSurveySectionService,
    private val surveySectionToSurveySectionDtoConverter: InfallibleConverter<SurveySection, SurveySectionDto>,
) {
    @QueryMapping
    suspend fun sections(surveyDto: SurveyDto): Flow<SurveySectionDto> {
        val surveySectionFlow = readSurveySectionService.getBySurveyId(surveyDto.id)
            ?: throw NullSubQueryException(surveyDto, ReadSurveySectionService::getBySurveyId)
        return surveySectionFlow.map { surveySectionToSurveySectionDtoConverter.convert(it) }
    }
}

