package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.email.EmailEntity
import com.leftindust.mockingbird.email.EmailToEmailDtoConverter
import com.leftindust.mockingbird.email.EmailType
import java.util.UUID

object EmailMother {
    val emailToEmailDtoConverter = EmailToEmailDtoConverter()

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

        val dto = emailToEmailDtoConverter.convert(entityDetached)
    }

    val dansEmailEntity = EmailEntity(
        type = DansEmail.emailType,
        address = DansEmail.address
    )


    private val `jenny's email type` = EmailType.Personal
    private const val `jenny's email address` = "jenny.e.white@example.com"
    val jennysEmail = EmailEntity(
        type = `jenny's email type`,
        address = `jenny's email address`
    )
}