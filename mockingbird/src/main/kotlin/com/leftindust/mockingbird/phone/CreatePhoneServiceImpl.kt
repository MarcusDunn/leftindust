package com.leftindust.mockingbird.phone

import com.leftindust.mockingbird.LogMessage
import javax.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
@Transactional
class CreatePhoneServiceImpl(private val phoneRepository: HibernatePhoneRepository) : CreatePhoneService {
    private val logger = LoggerFactory.getLogger(CreatePhoneServiceImpl::class.java)

    override suspend fun createPhone(createPhone: CreatePhone): Phone {
        val phone = Phone(createPhone.number, createPhone.type)
        return phoneRepository.save(phone)
            .also { logger.trace(LogMessage("Created $phone", "${CreatePhone::class.simpleName} was $createPhone").toString()) }
    }
}