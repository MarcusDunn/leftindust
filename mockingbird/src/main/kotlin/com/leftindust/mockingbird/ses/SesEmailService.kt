package com.leftindust.mockingbird.ses

import com.leftindust.mockingbird.validate.EmailAddress
import kotlinx.coroutines.Job

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
    ): Job

    companion object {
        /**
         * Send an HTML email using AWS Simple Email Service.
         *
         * @param html HTML email body.
         * @param targetEmail Address to send to. Address must be verified when
         * running in the SES sandbox.
         * @param from Address to send from. Address must be verified when running
         * in the SES sandbox.
         */
        suspend fun SesEmailService.sendHtmlEmail(
            subject: String,
            html: String,
            targetEmail: EmailAddress,
            from: EmailAddress
        ): Job {
            return sendHtmlEmail(subject, html, listOf(targetEmail), from)
        }
    }
}
