package com.leftindust.mockingbird.sns

import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.model.PublishRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class SnsTextServiceImpl(@Autowired private val snsClient: AmazonSNS) : SnsTextService {
    override suspend fun publishSMS(phoneNumber: String, message: String) {
        snsClient.publish(PublishRequest()
            .withPhoneNumber(phoneNumber)
            .withMessage(message)
        )
    }
}



