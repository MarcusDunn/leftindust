package com.leftindust.mockingbird

import com.leftindust.mockingbird.graphql.types.LocalDateDto
import java.time.LocalDate
import org.slf4j.LoggerFactory
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class LocalDateDtoToLocalDateConverter : FallibleConverter<LocalDateDto, LocalDate> {
    private val logger = LoggerFactory.getLogger(LocalDateDtoToLocalDateConverter::class.java)

    override fun convert(source: LocalDateDto): LocalDate? {
        return runCatching { LocalDate.parse(source.isoDateString) }
            .onFailure { throwable -> logger.warn(LogMessage("Returning null from ${LocalDateDtoToLocalDateConverter::class.simpleName} ${Converter<LocalDateDto, LocalDate>::convert.name}", throwable.toString()).toString()) }
            .getOrNull()
    }
}