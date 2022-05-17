package com.leftindust.mockingbird.event

import java.time.ZonedDateTime
import org.slf4j.LoggerFactory
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class EventFilterDtoToEventFilter(
    private val zonedDateTimeDtoToZonedDateTimeConverter: Converter<ZonedDateTimeDto, ZonedDateTime>,
) : Converter<EventFilterDto, EventFilter> {
    override fun convert(source: EventFilterDto): EventFilter? {
        return EventFilter(
            before = zonedDateTimeDtoToZonedDateTimeConverter.convert(source.before) ?: return null,
            after = zonedDateTimeDtoToZonedDateTimeConverter.convert(source.after) ?: return null,
        )
    }
}