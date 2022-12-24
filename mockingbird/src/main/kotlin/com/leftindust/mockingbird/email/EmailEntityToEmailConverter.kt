package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.ConversionError.Companion.ConversionFailure
import com.leftindust.mockingbird.NullEntityIdInConverterException
import com.leftindust.mockingbird.validate.EmailAddress
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.onFailure
import dev.forkhandles.values.ofResult4k
import java.util.*


fun EmailEntity.toEmail(): Result4k<Email, ConversionError<EmailEntity, Email>> {
    return Success(
        EmailImpl(
            id = id ?: throw NullEntityIdInConverterException(this),
            type = type,
            address = EmailAddress
                .ofResult4k(address)
                .onFailure { return ConversionFailure(it.reason) }
        )
    )
}

private data class EmailImpl(
    override val id: UUID,
    override val type: EmailType,
    override val address: EmailAddress,
) : Email