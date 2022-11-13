package com.leftindust.mockingbird.sns

import com.amazonaws.services.sns.AmazonSNS


import org.springframework.stereotype.Service

@Service
class SnsTextServiceImpl(
    private val sns: AmazonSNS
) : SnsTextService {
    override suspend fun pubTextSMS(messageVal: String?, phoneNumberVal: String?) {

    }

}



