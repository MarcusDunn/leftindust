package com.leftindust.mockingbird.ses

import com.leftindust.mockingbird.validate.EmailAddress
import kotlinx.coroutines.*
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import javax.mail.internet.MimeMessage

@Service
class SesEmailServiceImpl(
    private val emailSender: JavaMailSender
): SesEmailService {
    override suspend fun sendHtmlEmail(
        subject: String,
        html: String,
        targetEmails: List<EmailAddress>,
        from: String
    ) {
        withContext(Dispatchers.IO) {
            targetEmails.map {
                async {
                    val message: MimeMessage = emailSender.createMimeMessage()
                    val helper = MimeMessageHelper(message, "utf-8")

                    helper.setFrom(from)
                    helper.setSubject(subject)
                    helper.setText(html, true)

                    helper.setTo(it.value)
                    emailSender.send(message)
                }
            }.awaitAll()
        }
    }

    override suspend fun sendHtmlEmail(
        subject: String,
        html: String,
        targetEmail: EmailAddress,
        from: String
    ) {
        sendHtmlEmail(subject, html, listOf(targetEmail), from)
    }
}