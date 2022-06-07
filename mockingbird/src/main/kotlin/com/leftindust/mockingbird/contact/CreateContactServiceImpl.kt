package com.leftindust.mockingbird.contact

import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class CreateContactServiceImpl(
    private val contactRepository: ContactRepository,
) : CreateContactService {
    override suspend fun createContact(createContact: CreateContact): Contact {
        TODO()
    }
}