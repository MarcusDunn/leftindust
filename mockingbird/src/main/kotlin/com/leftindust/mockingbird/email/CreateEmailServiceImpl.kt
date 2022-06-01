package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.CreatedEntityMessage
import com.leftindust.mockingbird.LogMessage
import javax.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
@Transactional
class CreateEmailServiceImpl(private val emailRepository: EmailRepository) : CreateEmailService {
    private val logger = LoggerFactory.getLogger(CreateEmailServiceImpl::class.java)

    override suspend fun createEmail(createEmail: CreateEmail): Email {
        val email = Email(createEmail.type, createEmail.email)
        return emailRepository.save(email)
            .also { logger.trace(CreatedEntityMessage(email, "received $createEmail").toString()) }
    }
}