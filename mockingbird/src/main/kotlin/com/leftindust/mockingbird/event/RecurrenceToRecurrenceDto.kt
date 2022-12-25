package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success


fun Reoccurrence.toRecurrenceDto(): Result4k<RecurrenceDto, ConversionError<Reoccurrence, RecurrenceDto>> {
    return Success(
        RecurrenceDto(
            startDate = this.startDate,
            endDate = this.endDate,
            daysOfWeek = this.days,
        )
    )
}