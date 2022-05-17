package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import org.springframework.stereotype.Component

@Component
class SurveyToSurveyDtoConverter : InfallibleConverter<Survey, SurveyDto> {
    override fun convert(source: Survey): SurveyDto {
        return SurveyDto(
            SurveyDto.SurveyDtoId(source.id ?: throw NullEntityIdInConverterException(source)),
            source.name
        )
    }
}