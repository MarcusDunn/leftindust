package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class RecurrenceToRecurrenceDto : InfallibleConverter<Reoccurrence, RecurrenceDto> {
    override fun convert(source: Reoccurrence): RecurrenceDto {
        return RecurrenceDto(
            startDate = source.startDate,
            endDate = source.endDate,
            daysOfWeek = source.days,
        )
    }
}