package com.leftindust.mockingbird.email_service

import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class EmailSenderServiceImpl(
    private val emailSender: MailSender
): EmailSenderService {
    override suspend fun sendEmail(
        subject: String,
        text: String,
        targetEmail: String
    ) {

        val simpleMailMessage = SimpleMailMessage()
        simpleMailMessage.from = "kelvin@leftindust.com"
        simpleMailMessage.subject = subject
        simpleMailMessage.text = text
        simpleMailMessage.setTo("kelvincao6@gmail.com")

        emailSender.send(simpleMailMessage)
    }
}