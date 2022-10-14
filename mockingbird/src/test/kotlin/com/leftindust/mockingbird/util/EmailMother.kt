package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.email.CreateEmailDto
import com.leftindust.mockingbird.email.CreateEmailDtoToCreateEmailFallibleConverter
import com.leftindust.mockingbird.email.EmailEntity
import com.leftindust.mockingbird.email.EmailEntityToEmailConverter
import com.leftindust.mockingbird.email.EmailToEmailDtoConverter
import com.leftindust.mockingbird.email.EmailType
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
    }
}