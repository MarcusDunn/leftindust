package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class SurveySectionQueryController(
    private val readSurveySectionService: ReadSurveySectionService,
    private val surveySectionToSurveySectionDtoConverter: InfallibleConverter<SurveySection, SurveySectionDto>,
) {
    @QueryMapping
    suspend fun sections(surveyDto: SurveyDto): List<SurveySectionDto> {
        val surveySections = readSurveySectionService.getBySurveyId(surveyDto.id)
            ?: throw NullSubQueryException(surveyDto, ReadSurveySectionService::getBySurveyId)
        return surveySections.map { surveySectionToSurveySectionDtoConverter.convert(it) }
    }
}

