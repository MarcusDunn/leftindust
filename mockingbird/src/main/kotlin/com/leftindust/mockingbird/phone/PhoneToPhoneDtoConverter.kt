package com.leftindust.mockingbird.phone

import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success

fun Phone.toPhoneDto(): Result4k<PhoneDto, ConversionError<Phone, PhoneDto>> {
    return Success(
        PhoneDto(
            PhoneDto.PhoneDtoId(
                id
                    ?: throw IllegalArgumentException("Source ${Phone::class.simpleName} must have an id but was $this")
            ),
            number,
            type
        )
    )
}


