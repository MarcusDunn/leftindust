package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.FailedConversionMessage.Companion.FailedConversionMessage
import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.validate.EmailAddress
import dev.forkhandles.result4k.peekFailure
import dev.forkhandles.result4k.valueOrNull
import dev.forkhandles.values.ofResult4k
import mu.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger { }

@Component
class CreateEmailDtoToCreateEmailFallibleConverter : FallibleConverter<CreateEmailDto, CreateEmail> {
    override fun convert(source: CreateEmailDto): CreateEmail? {
        return CreateEmailImpl(
            type = source.type,
            email = EmailAddress
                .ofResult4k(source.email.lowercase())
                .peekFailure { logger.warn { FailedConversionMessage(source) } }
                .valueOrNull() ?: return null
        )
    }

    data class CreateEmailImpl(
        override val type: EmailType,
        override val email: EmailAddress,
    ) : CreateEmail

}