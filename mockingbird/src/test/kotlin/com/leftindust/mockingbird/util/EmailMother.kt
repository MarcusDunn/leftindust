package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.email.*
import dev.forkhandles.result4k.onFailure
import dev.forkhandles.result4k.valueOrNull
import java.util.*

object EmailMother {
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

        val domain = entityDetached.toEmail().onFailure { throw it.reason.toMockingbirdException() }
        val dto = domain.toEmailDto().onFailure { throw it.reason.toMockingbirdException() }

        val createDto = CreateEmailGraphQlDto(
            type = emailType,
            email = address
        )


        val createUpdatedDto = CreateEmailGraphQlDto(
            type = EmailType.Personal,
            email = "NewDanEmail@example.com"

        )
        val createDomain = createDto.toCreateEmail().valueOrNull()!!
    }
}