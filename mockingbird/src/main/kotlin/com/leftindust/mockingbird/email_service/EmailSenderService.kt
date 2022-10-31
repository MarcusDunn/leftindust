package com.leftindust.mockingbird.email_service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class EmailSenderService(
    private val emailSender: JavaMailSender
) {
    @Async
    suspend fun sendEmail(
        subject: String,
        text: String,
        targetEmail: String
    ) {
        val message = SimpleMailMessage()
        message.from = "hello@leftindust.com"
        message.subject = subject
        message.text = text
        message.setTo(targetEmail)

        emailSender.send(message)
    }
}