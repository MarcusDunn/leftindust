package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.validate.EmailAddress
import java.util.UUID

interface Email {
    val id: UUID
    val type: EmailType
    val address: EmailAddress
}
