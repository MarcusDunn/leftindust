package com.leftindust.mockingbird.email_service

interface EmailSenderService {
    suspend fun sendEmail(subject: String, text: String, targetEmail: String)
}