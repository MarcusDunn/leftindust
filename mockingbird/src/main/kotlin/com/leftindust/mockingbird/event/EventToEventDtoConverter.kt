package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.NullEntityIdInConverterException
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success

fun Event.toEventDto(): Result4k<EventDto, ConversionError<Event, EventDto>> {
    return Success(
        EventDto(
            id = EventDto.EventDtoId(this.id ?: throw NullEntityIdInConverterException(this)),
            iCal = this.ical
        )
    )
}