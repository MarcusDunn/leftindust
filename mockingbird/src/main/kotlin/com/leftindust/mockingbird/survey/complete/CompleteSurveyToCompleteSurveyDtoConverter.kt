package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class CompleteSurveyToCompleteSurveyDtoConverter(
    private val completeSurveySectionDtoConverter: InfallibleConverter<CompleteSurveySection, CompleteSurveySectionDto>
): InfallibleConverter<CompleteSurvey, CompleteSurveyDto> {
    override fun convert(source: CompleteSurvey): CompleteSurveyDto {
        return CompleteSurveyDto(
            id = CompleteSurveyDto.CompleteSurveyDtoId(source.id),
            sections = source.sections.map {
                completeSurveySectionDtoConverter.convert(it)
            }
        )
    }
}