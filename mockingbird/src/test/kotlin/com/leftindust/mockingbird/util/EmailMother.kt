package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.email.*
import java.util.UUID

object EmailMother {
    val emailEntityToEmailConverter = EmailEntityToEmailConverter()
    val emailToEmailDtoConverter = EmailToEmailDtoConverter()
    val createEmailDtoToCreateEmailConverter = CreateEmailDtoToCreateEmailFallibleConverter()

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
            type = EmailType.Personal,
            email = "NewEmail@gmail.com"
        )
        val create: CreateEmail = createEmailDtoToCreateEmailConverter.convert(createDto)!!
    }
}