package com.leftindust.mockingbird.sns

import org.springframework.stereotype.Service

@Service
class SnsTextServiceImpl : SnsTextService {
    override suspend fun pubTextSMS(messageVal: String?, phoneNumberVal: String?) {

    }
}



