package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.PersistenceError
import com.leftindust.mockingbird.email.CreateEmailService
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.patient.PatientEntity
import com.leftindust.mockingbird.patient.PatientRepository
import com.leftindust.mockingbird.person.CreateNameInfoService
import com.leftindust.mockingbird.phone.CreatePhoneService
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import org.springframework.data.repository.findByIdOrNull
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
@Transactional
class CreateContactServiceImpl(
    private val contactRepository: ContactRepository,
    private val patientRepository: PatientRepository,
    private val createPhoneService: CreatePhoneService,
    private val createEmailService: CreateEmailService,
    private val createNameInfoService: CreateNameInfoService
) : CreateContactService {
    override suspend fun createContact(
        createContact: CreateContact,
        patientId: UUID
    ): Result4k<Contact, PersistenceError> {
        val patient =
            patientRepository.findByIdOrNull(patientId) ?: return PersistenceError.FindError.invoke(
                PatientEntity::class,
                patientId
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

    override suspend fun createContact(createContact: CreateContact, patient: Patient): Contact {
        val patientEntity =
            patientRepository.findByIdOrNull(patient.id)
                ?: throw RuntimeException("Could not find patient corresponding to $patient")
        val contact = Contact(
            nameInfoEntity = createNameInfoService.createNameInfo(createContact.nameInfo),
            patientEntity = patientEntity,
            relationship = createContact.relationship,
            phone = createContact.phones.map { createPhoneService.createPhone(it) }.toMutableSet(),
            email = createContact.emails.map { createEmailService.createEmail(it) }.toMutableSet()
        )
        return contactRepository.save(contact)
    }
}