package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.CreatedEntityMessage
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
@Transactional
class CreateEmailServiceImpl(private val emailRepository: EmailRepository) : CreateEmailService {
    private val logger = KotlinLogging.logger { }

    override suspend fun createEmail(createEmail: CreateEmail): EmailEntity {
        val email = EmailEntity(createEmail.type, createEmail.email.value)
        return emailRepository.save(email)
            .also { logger.trace { CreatedEntityMessage(email) } }
    }
}