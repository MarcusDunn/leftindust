package com.leftindust.mockingbird.sns

import com.leftindust.mockingbird.validate.PhoneNumber

interface SnsTextService {
    suspend fun publishSMS(phoneNumber: PhoneNumber, message: String)
}