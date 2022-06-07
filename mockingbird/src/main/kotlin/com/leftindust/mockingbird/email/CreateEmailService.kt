package com.leftindust.mockingbird.email

interface CreateEmailService {
    suspend fun createEmail(createEmail: CreateEmail): Email
}
