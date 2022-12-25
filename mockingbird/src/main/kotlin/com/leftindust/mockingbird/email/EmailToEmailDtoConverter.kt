package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success


fun Email.toEmailDto(): Result4k<EmailDto, ConversionError<Email, EmailDto>> {
    return Success(
        EmailDto(
            EmailDto.EmailDtoId(this.id),
            this.type,
            this.address.value
        )
    )
}
