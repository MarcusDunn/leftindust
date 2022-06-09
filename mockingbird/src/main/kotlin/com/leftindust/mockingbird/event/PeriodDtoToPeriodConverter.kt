package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.FailedConversionMessage.Companion.FailedConversionMessage
import java.time.Period
import mu.KotlinLogging
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class PeriodDtoToPeriodConverter : Converter<PeriodDto, Period> {
    val logger = KotlinLogging.logger { }

    override fun convert(source: PeriodDto): Period? {
        return runCatching { Period.parse(source.periodIsoString) }
            .onFailure { FailedConversionMessage(source) }
            .getOrNull()
    }
}