package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.contact.ContactDto
import com.leftindust.mockingbird.contact.ReadContactService
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.DoctorRepository
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.PatientRepository
import dev.forkhandles.result4k.onFailure
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Transactional
@Repository
class ReadEmailServiceImpl(
    val emailRepository: EmailRepository,
    val patientRepository: PatientRepository,
    val readContactService: ReadContactService,
    val doctorRepository: DoctorRepository,
) : ReadEmailService {
    private val logger = KotlinLogging.logger { }
    override suspend fun getByEmailId(emailId: EmailDto.EmailDtoId): Email? {
        val emailEntity = emailRepository.findByIdOrNull(emailId.value)
            ?: return null
        return emailEntity.toEmail().onFailure { throw it.reason.toMockingbirdException() }
    }

    override suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): List<Email>? {
        val doctor = doctorRepository.findByIdOrNull(doctorId.value)
            ?: return null
        return doctor.emails.map { it.toEmail().onFailure { throw it.reason.toMockingbirdException() } }
    }

    override suspend fun getPatientEmails(patientId: PatientDto.PatientDtoId): List<Email>? {
        val patientEntity = patientRepository.findByIdOrNull(patientId.value)
            ?: return null
        return patientEntity.emails.map { it.toEmail().onFailure { throw it.reason.toMockingbirdException() } }
    }

    override suspend fun getContactEmails(contactContactDtoId: ContactDto.ContactDtoId): List<Email>? {
        val contactEntity = readContactService.getByContactId(contactContactDtoId)
            ?: return null
        return contactEntity.email.map { it.toEmail().onFailure { throw it.reason.toMockingbirdException() } }
    }
}
