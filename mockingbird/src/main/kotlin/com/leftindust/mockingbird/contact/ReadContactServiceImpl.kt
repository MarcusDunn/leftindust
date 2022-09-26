package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.PatientRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class ReadContactServiceImpl(
    @Autowired private val contactRepository: ContactRepository,
    @Autowired private val patientRepository: PatientRepository
) : ReadContactService {
    private val logger = KotlinLogging.logger { }

    override suspend fun getByPatientId(patientDtoId: PatientDto.PatientDtoId): List<Contact>? {
        val patient = patientRepository.findByIdOrNull(patientDtoId.value) ?: return null
        return patient.contacts.toList()
    }

    override suspend fun getByContactId(contactDtoId: ContactDto.ContactDtoId): Contact? {
        return contactRepository.findById(contactDtoId.value).orElse(null)
    }
}