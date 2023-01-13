package com.leftindust.mockingbird.ses

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService
import com.amazonaws.services.simpleemail.model.*
import com.leftindust.mockingbird.validate.EmailAddress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class SesEmailServiceImpl(
    private val emailSender: JavaMailSender,
    private val sesClient: AmazonSimpleEmailService,
    private val scope: CoroutineScope
) : SesEmailService {
    override suspend fun sendHtmlEmail(
        subject: String,
        html: String,
        targetEmails: List<EmailAddress>,
        from: EmailAddress
    ): Job = scope.launch {
        val emailRequest: SendEmailRequest = SendEmailRequest()
            .withSource(from.value)
            .withDestination(Destination(targetEmails.map { it.value }))
            .withMessage(
                Message()
                    .withBody(Body().withHtml(Content().withCharset("UTF-8").withData(html)))
                    .withSubject(Content().withCharset("UTF-8").withData(subject))
            )
        sesClient.sendEmail(emailRequest)
    }

//        withContext(Dispatchers.IO) {
//            targetEmails.map {
//                async {
//                    val message: MimeMessage = emailSender.createMimeMessage()
//                    val helper = MimeMessageHelper(message, "utf-8")
//
//                    helper.setFrom(from.value)
//                    helper.setSubject(subject)
//                    helper.setText(html, true)
//
//                    helper.setTo(it.value)
//                    emailSender.send(message)
//                }
//            }.awaitAll()
//        }
}

