package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.LogMessage
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class ReadContactServiceImpl(
    @Autowired private val contactRepository: ContactRepository,
    @Autowired private val readPatientService: ReadPatientService,
) : ReadContactService {
    private val logger = LoggerFactory.getLogger(ReadContactServiceImpl::class.java)

    override suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): Flow<Contact>? {
        val patient = readPatientService.getByPatientId(patientId) ?: run {
            logger.trace(LogMessage("Returning null from ${ReadContactService::getByPatientId.name}", "${ReadPatientService::class.simpleName}.${ReadPatientService::getByPatientId.name} returned null for $patientId").toString())
            return null
        }
        return patient.contacts.asFlow()
    }

    override suspend fun getByContactId(contactId: ContactDto.Id): Contact? {
        return contactRepository.findById(contactId.value).orElse(null) ?: run {
            logger.trace(LogMessage("Returning null from ${ReadContactService::getByContactId.name}", "${ContactRepository::class.simpleName}.${ContactRepository::findById.name} returned null for $contactId").toString())
            null
        }
    }
}