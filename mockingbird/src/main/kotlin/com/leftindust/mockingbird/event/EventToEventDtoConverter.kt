package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import org.springframework.stereotype.Component

@Component
class EventToEventDtoConverter : InfallibleConverter<Event, EventDto> {
    override fun convert(source: Event): EventDto {
        return EventDto(
            id = EventDto.EventDtoId(source.id ?: throw NullEntityIdInConverterException(source)),
            iCal = source.ical
        )
    }
}