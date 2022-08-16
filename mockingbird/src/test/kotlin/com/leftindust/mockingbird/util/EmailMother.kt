package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.email.Email
import com.leftindust.mockingbird.email.EmailType

object EmailMother {
    object DansEmail {
        val emailType = EmailType.Work
        val address = "dan.shervershani@example.com"
        val entityPersisted = Email(
            type = emailType,
            address = address
        )
    }

    val dansEmail = Email(
        type = DansEmail.emailType,
        address = DansEmail.address
    )


    private val `jenny's email type` = EmailType.Personal
    private const val `jenny's email address` = "jenny.e.white@example.com"
    val jennysEmail = Email(
        type = `jenny's email type`,
        address = `jenny's email address`
    )
}