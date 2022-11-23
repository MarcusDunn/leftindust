package com.leftindust.mockingbird.ses

import com.leftindust.mockingbird.validate.EmailAddress

suspend fun SesEmailService.sendHtmlEmail(
    subject: String,
    html: String,
    targetEmail: EmailAddress,
    from: EmailAddress
) {
    sendHtmlEmail(subject, html, listOf(targetEmail), from)
}