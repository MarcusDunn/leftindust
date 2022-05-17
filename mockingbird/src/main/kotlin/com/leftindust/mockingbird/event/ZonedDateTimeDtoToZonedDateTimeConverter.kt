package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.LogMessage
import java.time.ZonedDateTime
import org.slf4j.LoggerFactory
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class ZonedDateTimeDtoToZonedDateTimeConverter : Converter<ZonedDateTimeDto, ZonedDateTime> {
    private val logger = LoggerFactory.getLogger(ZonedDateTimeDtoToZonedDateTimeConverter::class.java)

    override fun convert(source: ZonedDateTimeDto): ZonedDateTime? {
        return runCatching { ZonedDateTime.parse(source.isoDateTimeString) }
            .onFailure { logger.trace(LogMessage("returning null from ${ZonedDateTimeDtoToZonedDateTimeConverter::class.simpleName}.${ZonedDateTimeDtoToZonedDateTimeConverter::convert.name}", it.message ?: it.toString()).toString()) }
            .getOrNull()
    }
}