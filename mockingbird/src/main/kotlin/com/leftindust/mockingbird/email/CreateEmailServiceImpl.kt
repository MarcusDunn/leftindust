package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.CreatedEntityMessage
import javax.transaction.Transactional
import mu.KotlinLogging
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
@Transactional
class CreateEmailServiceImpl(private val emailRepository: EmailRepository) : CreateEmailService {
    private val logger = KotlinLogging.logger { }

    override suspend fun createEmail(createEmail: CreateEmail): Email {
        val email = Email(createEmail.type, createEmail.email)
        return emailRepository.save(email)
            .also { logger.trace { CreatedEntityMessage(email) } }
    }
}