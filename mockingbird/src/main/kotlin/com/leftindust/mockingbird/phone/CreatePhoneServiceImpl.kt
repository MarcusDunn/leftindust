package com.leftindust.mockingbird.phone

import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
@Transactional
class CreatePhoneServiceImpl(private val phoneRepository: PhoneRepository) : CreatePhoneService {
    private val logger = KotlinLogging.logger { }

    override suspend fun createPhone(createPhone: CreatePhone): Phone {
        val phone = Phone(createPhone.number.value, createPhone.type)
        return phoneRepository.save(phone)
            .also { logger.trace { "Created $phone" } }
    }
}