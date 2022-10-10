package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.EntityNotFoundException
import com.leftindust.mockingbird.MockingbirdException
import com.leftindust.mockingbird.NullEntityIdInConverterException
import com.leftindust.mockingbird.email.CreateEmailService
import com.leftindust.mockingbird.patient.CreateContactPatient
import com.leftindust.mockingbird.patient.PatientEntity
import com.leftindust.mockingbird.patient.PatientRepository
import com.leftindust.mockingbird.person.CreateNameInfoService
import com.leftindust.mockingbird.person.NameInfoEntity
import com.leftindust.mockingbird.phone.CreatePhoneService
import dev.forkhandles.result4k.Failure
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
    override suspend fun createContact(createContact: CreateContactPatient): Result4k<Contact, MockingbirdException> {
        val patient = patientRepository.findByIdOrNull(createContact.patientId) ?: return Failure(
            EntityNotFoundException(createContact.patientId, PatientEntity::class)
        )
        val contact = Contact(
            nameInfoEntity = createNameInfoService.createNameInfo(createContact.nameInfo),
            patient = patient,
            relationship = createContact.relationship,
            phone = createContact.phones.map { createPhoneService.createPhone(it) }.toMutableSet(),
            email = createContact.emails.map { createEmailService.createEmail(it) }.toMutableSet()
        )
        return Success(contactRepository.save(contact))
    }
}