package com.leftindust.mockingbird.ses

import com.leftindust.mockingbird.validate.EmailAddress
import jakarta.mail.internet.MimeMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class SesEmailServiceImpl(
    private val emailSender: JavaMailSender
): SesEmailService {
    override suspend fun sendHtmlEmail(
        subject: String,
        html: String,
        targetEmails: List<EmailAddress>,
        from: EmailAddress
    ) {
        withContext(Dispatchers.IO) {
            targetEmails.map {
                async {
                    val message: MimeMessage = emailSender.createMimeMessage()
                    val helper = MimeMessageHelper(message, "utf-8")

                    helper.setFrom(from.value)
                    helper.setSubject(subject)
                    helper.setText(html, true)

                    helper.setTo(it.value)
                    emailSender.send(message)
                }
            }.awaitAll()
        }
    }
}