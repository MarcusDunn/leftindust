package com.leftindust.mockingbird.visit

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import org.springframework.stereotype.Component

@Component
class VisitToVisitDtoConverter: InfallibleConverter<Visit, VisitDto> {
    override fun convert(source: Visit): VisitDto {
        return VisitDto(
            id = VisitDto.VisitDtoId(source.id ?: throw NullEntityIdInConverterException(source)),
            title = source.title,
            description = source.description
        )
    }

}