package com.leftindust.mockingbird.ses

import com.leftindust.mockingbird.validate.EmailAddress

interface SesEmailService {
    /**
     * Send an HTML email using AWS Simple Email Service.
     *
     * @param html HTML email body.
     * @param targetEmails Addresses to send to. Addresses must be verified when
     * running in the SES sandbox.
     * @param from Address to send from. Address must be verified when running
     * in the SES sandbox.
     */
    suspend fun sendHtmlEmail(
        subject: String,
        html: String,
        targetEmails: List<EmailAddress>,
        from: EmailAddress
    )
}