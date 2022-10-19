package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.ConversionError.Companion.ConversionFailure
import com.leftindust.mockingbird.email.*
import dev.forkhandles.result4k.onFailure
import java.util.UUID

object EmailMother {
    val emailEntityToEmailConverter = EmailEntityToEmailConverter()
    val emailToEmailDtoConverter = EmailToEmailDtoConverter()
    val createEmailDtoConverter = CreateEmailDtoToCreateEmailFallibleConverter()

    object DansEmail {
        val id = UUID.fromString("10845c82-1d17-11ed-861d-0242ac120002")
        val emailType = EmailType.Work
        val address = "dan.shervershani@example.com"
        val entityDetached: EmailEntity
            get() = EmailEntity(
                type = emailType,
                address = address
            ).apply { id = this@DansEmail.id }

        val entityTransient: EmailEntity
            get() = EmailEntity(
                type = emailType,
                address = address
            )

        val domain = emailEntityToEmailConverter.convert(entityDetached)
        val dto = emailToEmailDtoConverter.convert(domain)

        val createDto = CreateEmailDto(
            email = "dan.shervershani@example.com",
            type = EmailType.Work
        )

        val createDomain = createEmailDtoConverter.convert(createDto)!!

        val createDto = CreateEmailDto(
            type = emailType,
            email = address
        )

        val createUpdatedDto = CreateEmailDto(
            type = EmailType.Personal,
            email = "NewDanEmail@example.com"

        )
    }
}