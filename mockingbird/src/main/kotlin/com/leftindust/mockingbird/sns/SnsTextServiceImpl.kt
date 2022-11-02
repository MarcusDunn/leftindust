package com.leftindust.mockingbird.sns

import aws.sdk.kotlin.services.sns.SnsClient
import aws.sdk.kotlin.services.sns.model.PublishRequest


import org.springframework.stereotype.Service

@Service
class SnsTextServiceImpl(
) : SnsTextService {
    override suspend fun pubTextSMS(messageVal: String?, phoneNumberVal: String?) {
        val request = PublishRequest {
            message = messageVal
            phoneNumber = phoneNumberVal
        }

        SnsClient { region = "us-east-1" }.use { snsClient ->
            val result = snsClient.publish(request)
            println("${result.messageId} message sent.")
        }
    }

}



