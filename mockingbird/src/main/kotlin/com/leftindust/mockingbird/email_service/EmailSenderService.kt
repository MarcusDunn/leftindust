package com.leftindust.mockingbird.email_service

import com.leftindust.mockingbird.validate.EmailAddress

interface EmailSenderService {
    suspend fun sendHtmlEmail(subject: String, html: String, targetEmails: List<EmailAddress>)
}