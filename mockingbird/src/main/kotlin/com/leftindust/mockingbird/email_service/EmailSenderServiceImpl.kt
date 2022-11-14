package com.leftindust.mockingbird.email_service

import com.leftindust.mockingbird.validate.EmailAddress
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import javax.mail.internet.MimeMessage

@Service
class EmailSenderServiceImpl(
    private val mailSender: JavaMailSender
): EmailSenderService {
    @Async
    override suspend fun sendEmail(template: EmailTemplate, targetEmails: MutableList<EmailAddress>) {
        val message: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, "utf-8")

        helper.setFrom("kelvin@leftindust.com")
        helper.setBcc(targetEmails.map { email -> email.value }.toTypedArray())
        helper.setText(template.html, true)
//        helper.setText(template.text)
        helper.setSubject(template.subject)
        // Add multipart with plaintext

        mailSender.send(message)
    }
}