package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import java.time.ZonedDateTime

fun ZonedDateTime.toZonedDateTimeDto(): Result4k<ZonedDateTimeDto, ConversionError<ZonedDateTime, ZonedDateTimeDto>> {
    return Success(
        ZonedDateTimeDto(this.toString())
    )
}