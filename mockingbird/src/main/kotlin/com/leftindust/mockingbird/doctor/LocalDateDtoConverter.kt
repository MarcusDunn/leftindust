package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.graphql.types.LocalDateDto
import java.time.LocalDate
import org.springframework.stereotype.Component

@Component
class LocalDateDtoConverter : InfallibleConverter<LocalDate, LocalDateDto> {
    override fun convert(source: LocalDate): LocalDateDto {
        return LocalDateDto(source.toString())
    }
}