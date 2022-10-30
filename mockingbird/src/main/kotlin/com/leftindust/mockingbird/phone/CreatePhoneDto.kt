package com.leftindust.mockingbird.phone

import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success

interface CreatePhoneDto {
    val number: String
    val type: PhoneType
}

fun CreatePhoneDto.toCreatePhone(): Result4k<CreatePhone, ConversionError<CreatePhoneDto, CreatePhone>> {
    return Success(
        CreatePhoneImpl(
            number = number, type = type
        )
    )
}

data class CreatePhoneImpl(
    override val number: String,
    override val type: PhoneType
) : CreatePhone

data class CreatePhoneGraphQlDto (
    override val number: String,
    override val type: PhoneType,
) : CreatePhoneDto


