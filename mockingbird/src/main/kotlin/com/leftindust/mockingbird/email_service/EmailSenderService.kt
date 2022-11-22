package com.leftindust.mockingbird.email_service

import com.leftindust.mockingbird.validate.EmailAddress

interface EmailSenderService {
    /**
     * Send an HTML email using AWS Simple Email Service.
     *
     * @param html Email body in HTML.
     * @param targetEmails Addresses to send to. Addresses must be verified when
     * running in the SES sandbox.
     * @param from Address to send from. Address must be verified when running
     * in the SES sandbox.
     */
    suspend fun sendHtmlEmail(
        subject: String,
        html: String,
        targetEmails: List<EmailAddress>,
        from: String = "hello@leftindust.com")
    /**
     * Send an HTML email using AWS Simple Email Service.
     *
     * @param html Email body in HTML.
     * @param targetEmails Address to send to. Address must be verified when
     * running in the SES sandbox.
     * @param from Address to send from. Address must be verified when running
     * in the SES sandbox.
     */
    suspend fun sendHtmlEmail(
        subject: String,
        html: String,
        targetEmails: EmailAddress,
        from: String = "hello@leftindust.com",)
}