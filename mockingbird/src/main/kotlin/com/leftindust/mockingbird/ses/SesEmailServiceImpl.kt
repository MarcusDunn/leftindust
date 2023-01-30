package com.leftindust.mockingbird.ses

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService
import com.amazonaws.services.simpleemail.model.Body
import com.amazonaws.services.simpleemail.model.Content
import com.amazonaws.services.simpleemail.model.Destination
import com.amazonaws.services.simpleemail.model.Message
import com.amazonaws.services.simpleemail.model.SendEmailRequest
import com.leftindust.mockingbird.validate.EmailAddress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class SesEmailServiceImpl(
    private val emailSender: JavaMailSender,
    private val sesClient: AmazonSimpleEmailService
) : SesEmailService {
    override suspend fun sendHtmlEmail(
        subject: String,
        html: String,
        targetEmails: List<EmailAddress>,
        from: EmailAddress
    ) {
        CoroutineScope(Dispatchers.IO).launch {
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
}
