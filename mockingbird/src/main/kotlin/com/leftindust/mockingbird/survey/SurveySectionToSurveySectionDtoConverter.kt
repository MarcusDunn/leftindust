package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import org.springframework.stereotype.Component

@Component
class SurveySectionToSurveySectionDtoConverter : InfallibleConverter<SurveySection, SurveySectionDto> {
    override fun convert(source: SurveySection): SurveySectionDto {
        return SurveySectionDto(
            id = SurveySectionDto.SurveySectionDtoId(source.id ?: throw NullEntityIdInConverterException(source)),
            name = source.name,
            number = source.number,
            description = source.description,
        )
    }
}