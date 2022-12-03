package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.ConversionError.Companion.ConversionFailure
import com.leftindust.mockingbird.validate.EmailAddress
import dev.forkhandles.result4k.*
import dev.forkhandles.values.ofResult4k

fun CreateEmailDto.toCreateEmail(): Result4k<CreateEmail, ConversionError<CreateEmailDto, CreateEmail>> {
    return Success(CreateEmailImpl(
        type = type,
        email = EmailAddress
            .ofResult4k(email)
            .onFailure { return ConversionFailure(it.reason) }
        )
    )
}

private data class CreateEmailImpl(
    override val type: EmailType,
    override val email: EmailAddress,
) : CreateEmail
