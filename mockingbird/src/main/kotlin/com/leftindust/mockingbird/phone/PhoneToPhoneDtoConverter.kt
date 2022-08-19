package com.leftindust.mockingbird.phone

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class PhoneToPhoneDtoConverter : InfallibleConverter<Phone, PhoneDto> {
    override fun convert(source: Phone): PhoneDto {
        return PhoneDto(
            PhoneDto.PhoneDtoId(source.id ?: throw IllegalArgumentException("Source ${Phone::class.simpleName} must have an id but was $source")),
            source.number,
            source.type
        )
    }

}