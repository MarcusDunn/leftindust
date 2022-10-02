package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import com.leftindust.mockingbird.validate.EmailAddress
import dev.forkhandles.result4k.orThrow
import dev.forkhandles.values.ofResult4k
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class EmailEntityToEmailConverter : InfallibleConverter<EmailEntity, Email> {
    override fun convert(source: EmailEntity): Email {
        return EmailImpl(
            id = source.id ?: throw NullEntityIdInConverterException(source),
            type = source.type,
            address = EmailAddress.ofResult4k(source.address).orThrow(),
        )
    }

    data class EmailImpl(
        override val id: UUID,
        override val type: EmailType,
        override val address: EmailAddress,
    ) : Email
}
