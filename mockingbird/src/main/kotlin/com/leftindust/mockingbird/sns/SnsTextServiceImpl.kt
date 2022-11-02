package com.leftindust.mockingbird.sns

import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.AmazonSNSClient
import com.amazonaws.services.sns.model.PublishRequest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.system.exitProcess

@Service
class SnsTextServiceImpl(
    @Autowired private val snsClient: AmazonSNS
) : SnsTextService {
    override suspend fun pubTextSMS(messageVal: String?, phoneNumberVal: String?) {
        val request = PublishRequest(messageVal, phoneNumberVal)

        val result = snsClient.publish(request)
        println("${result.messageId} message sent.")
    }
}



