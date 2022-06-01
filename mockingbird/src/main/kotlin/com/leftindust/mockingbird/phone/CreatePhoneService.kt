package com.leftindust.mockingbird.phone

interface CreatePhoneService {
    suspend fun createPhone(createPhone: CreatePhone): Phone
}
