package com.leftindust.mockingbird.event

import java.time.Period
import mu.KotlinLogging
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class PeriodDtoToPeriodConverter : Converter<PeriodDto, Period> {
    val logger = KotlinLogging.logger { }

    override fun convert(source: PeriodDto): Period? {
        return runCatching { Period.parse(source.periodIsoString) }
            .onFailure { logger.debug { "failed to convert $source to a Period" } }
            .getOrNull()
    }
}