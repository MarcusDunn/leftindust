package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.PersistenceError
import com.leftindust.mockingbird.email.CreateEmailService
import com.leftindust.mockingbird.patient.PatientEntity
import com.leftindust.mockingbird.patient.PatientRepository
import com.leftindust.mockingbird.person.CreateNameInfoService
import com.leftindust.mockingbird.phone.CreatePhoneService
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import org.springframework.data.repository.findByIdOrNull
import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class CreateContactServiceImpl(
    private val contactRepository: ContactRepository,
    private val patientRepository: PatientRepository,
    private val createPhoneService: CreatePhoneService,
    private val createEmailService: CreateEmailService,
    private val createNameInfoService: CreateNameInfoService
) : CreateContactService {
    override suspend fun createContact(createContact: CreateContactPatient): Result4k<Contact, PersistenceError> {
        val patient =
            patientRepository.findByIdOrNull(createContact.patientId) ?: return PersistenceError.FindError.invoke(
                PatientEntity::class,
                createContact.patientId
            )
        val contact = Contact(
            nameInfoEntity = createNameInfoService.createNameInfo(createContact.nameInfo),
            patientEntity = patient,
            relationship = createContact.relationship,
            phone = createContact.phones.map { createPhoneService.createPhone(it) }.toMutableSet(),
            email = createContact.emails.map { createEmailService.createEmail(it) }.toMutableSet()
        )
        return Success(contactRepository.save(contact))
    }
}