package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.validate.EmailAddress
import dev.forkhandles.result4k.peekFailure
import dev.forkhandles.result4k.valueOrNull
import dev.forkhandles.values.ofResult4k
import mu.KotlinLogging

private val logger = KotlinLogging.logger { }


fun CreateEmailDto.toCreateEmail(): CreateEmail? {
    return CreateEmailImpl(
        type = type,
        email = EmailAddress
            .ofResult4k(email)
            .peekFailure { logger.warn { it } }
            .valueOrNull() ?: return null
    )
}

data class CreateEmailImpl(
    override val type: EmailType,
    override val email: EmailAddress,
) : CreateEmail
