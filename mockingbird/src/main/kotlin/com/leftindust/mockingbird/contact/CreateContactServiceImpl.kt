package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.email.CreateEmailService
import com.leftindust.mockingbird.person.CreateNameInfoService
import com.leftindust.mockingbird.phone.CreatePhoneService
import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class CreateContactServiceImpl(
    private val contactRepository: ContactRepository,
    private val createPhoneService: CreatePhoneService,
    private val createEmailService: CreateEmailService,
    private val createNameInfoService: CreateNameInfoService
) : CreateContactService {
    override suspend fun createContact(createContact: CreateContact): Contact {
        val contact = Contact(
            nameInfoEntity = createNameInfoService.createNameInfo(createContact.nameInfo),
            relationship = createContact.relationship,
            phone = createContact.phones.map { createPhoneService.createPhone(it) }.toMutableSet(),
            email = createContact.emails.map { createEmailService.createEmail(it) }.toMutableSet()
        )
        return contactRepository.save(contact)
    }
}