package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class CompleteSurveySectionInputToCompleteSurveySectionInputDtoConverter : InfallibleConverter<CompleteSurveySectionInput, CompleteSurveySectionInputDto> {
    override fun convert(source: CompleteSurveySectionInput): CompleteSurveySectionInputDto {
        return CompleteSurveySectionInputDto(
            id = CompleteSurveySectionInputDto.CompleteSurveySectionInputDtoId(source.id),
            value = source.value
        )
    }

}