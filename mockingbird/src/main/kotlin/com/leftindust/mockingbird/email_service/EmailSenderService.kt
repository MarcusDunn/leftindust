package com.leftindust.mockingbird.email_service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailSenderService(
    private val emailSender: JavaMailSender
) {
    fun sendEmail(
        subject: String,
        text: String,
        targetEmail: String
    ) {
        val message = SimpleMailMessage()
        message.from = "kelvin@leftindust.com"
        message.subject = subject
        message.text = text
        message.setTo(targetEmail)

        emailSender.send(message)
    }
}