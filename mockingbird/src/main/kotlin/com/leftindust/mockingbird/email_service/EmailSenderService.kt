package com.leftindust.mockingbird.email_service

import com.leftindust.mockingbird.validate.EmailAddress

interface EmailSenderService {
    suspend fun sendEmail(template: EmailTemplate, targetEmails: MutableList<EmailAddress>)
}