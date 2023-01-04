package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.email.EmailDto
import com.leftindust.mockingbird.phone.PhoneDto
import org.springframework.stereotype.Component

@Component
class ContactQuery(
    private val readContactService: ReadContactService,
) {
    suspend fun phones(): List<PhoneDto> = TODO()

    suspend fun emails(): List<EmailDto> = TODO()
}