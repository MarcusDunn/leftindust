package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.InfallibleConverter
import java.time.ZonedDateTime
import org.springframework.stereotype.Component

@Component
class ZonedDateTimeToZonedDateTimeDto : InfallibleConverter<ZonedDateTime, ZonedDateTimeDto> {
    override fun convert(source: ZonedDateTime): ZonedDateTimeDto {
        return ZonedDateTimeDto(source.toString())
    }
}