package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.graphql.types.LocalDateDto
import java.time.LocalDate
import org.springframework.stereotype.Component

@Component
class RecurrenceToRecurrenceDto(
    private val localDateToLocalDateDtoConverter: InfallibleConverter<LocalDate, LocalDateDto>,
) : InfallibleConverter<Reoccurrence, RecurrenceDto> {
    override fun convert(source: Reoccurrence): RecurrenceDto {
        return RecurrenceDto(
            startDate = localDateToLocalDateDtoConverter.convert(source.startDate),
            endDate = localDateToLocalDateDtoConverter.convert(source.endDate),
            daysOfWeek = source.days,
        )
    }
}