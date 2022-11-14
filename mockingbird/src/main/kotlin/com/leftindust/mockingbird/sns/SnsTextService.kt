package com.leftindust.mockingbird.sns

interface SnsTextService {
    suspend fun publishSMS(phoneNumber: String, message: String)
}