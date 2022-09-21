package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class CompleteSurveySectionToCompleteSurveySectionDtoConverter(
    private val completeSurveySectionInputToCompleteSurveySectionInputDtoConverter: InfallibleConverter<CompleteSurveySectionInput, CompleteSurveySectionInputDto>
) :
    InfallibleConverter<CompleteSurveySection, CompleteSurveySectionDto> {
    override fun convert(source: CompleteSurveySection): CompleteSurveySectionDto {
        return CompleteSurveySectionDto(
            id = CompleteSurveySectionDto.CompleteSurveySectionDtoId(source.id),
            inputs = source.inputs.map {
                completeSurveySectionInputToCompleteSurveySectionInputDtoConverter.convert(it)
            }
        )
    }
}