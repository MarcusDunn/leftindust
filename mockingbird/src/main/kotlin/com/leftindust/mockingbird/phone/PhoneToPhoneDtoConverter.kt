package com.leftindust.mockingbird.phone

import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success

fun Phone.toPhoneDto(): Result4k<PhoneDto, ConversionError<Phone, PhoneDto>> {
    return Success(
        PhoneDto(
            PhoneDto.PhoneDtoId(
                this.id
                    ?: throw IllegalArgumentException("Source ${Phone::class.simpleName} must have an id but was $this")
            ),
            this.number,
            this.type
        )
    )
}


