package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class ReadContactServiceImpl(
    @Autowired private val contactRepository: ContactRepository,
    @Autowired private val readPatientService: ReadPatientService,
) : ReadContactService {
    private val logger = KotlinLogging.logger {  }

    override suspend fun getByPatientId(patientDtoId: PatientDto.PatientDtoId): Flow<Contact>? {
        val patient = readPatientService.getByPatientId(patientDtoId) ?: run {
            logger.trace { "Returning null from getByPatientId. Could not find a patient with id $patientDtoId" }
            return null
        }
        return patient.contacts.asFlow()
    }

    override suspend fun getByContactId(contactDtoId: ContactDto.ContactDtoId): Contact? {
        return contactRepository.findById(contactDtoId.value).orElse(null) ?: run {
            logger.trace { "Returning null from getByContactId. Could not find a contact with id $contactDtoId" }
            null
        }
    }
}