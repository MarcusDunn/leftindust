package com.leftindust.mockingbird.phone

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.ConversionError.Companion.ConversionFailure
import com.leftindust.mockingbird.validate.PhoneNumber
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.onFailure
import dev.forkhandles.values.ofResult4k

interface CreatePhoneDto {
    val number: String
    val type: PhoneType
}

fun CreatePhoneDto.toCreatePhone(): Result4k<CreatePhone, ConversionError<CreatePhoneDto, CreatePhone>> {
    return Success(
        CreatePhoneImpl(
            number = PhoneNumber
                .ofResult4k(number)
                .onFailure { return ConversionFailure(it.reason) },
            type = type
        )
    )
}

data class CreatePhoneImpl(
    override val number: PhoneNumber,
    override val type: PhoneType
) : CreatePhone

data class CreatePhoneGraphQlDto (
    override val number: String,
    override val type: PhoneType,
) : CreatePhoneDto


