package com.leftindust.mockingbird.email_service

interface EmailTemplate {
    val subject: String
    val html: String
    val text: String
}