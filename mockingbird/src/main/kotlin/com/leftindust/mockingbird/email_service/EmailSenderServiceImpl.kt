package com.leftindust.mockingbird.email_service

import com.leftindust.mockingbird.validate.EmailAddress
import kotlinx.coroutines.*
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import javax.mail.internet.MimeMessage

@Service
class EmailSenderServiceImpl(
    private val emailSender: JavaMailSender
): EmailSenderService {
    override suspend fun sendHtmlEmail(subject: String, html: String, targetEmails: List<EmailAddress>) {
        withContext(Dispatchers.IO) {
            targetEmails.map {
                async {
                    val message: MimeMessage = emailSender.createMimeMessage()
                    val helper = MimeMessageHelper(message, "utf-8")

                    helper.setFrom("hello@leftindust.com")
                    helper.setSubject(subject)
                    helper.setText(html, true)

                    helper.setTo(it.value)
                    emailSender.send(message)
                }
            }.awaitAll()
        }
    }

}