package com.leftindust.mockingbird.sns


interface SnsTextService {
    suspend fun pubTextSMS(messageVal: String?, phoneNumberVal: String?)
}