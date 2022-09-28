package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.contact.ContactDto
import com.leftindust.mockingbird.contact.ReadContactService
import com.leftindust.mockingbird.doctor.DoctorRepository
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.PatientRepository
import javax.transaction.Transactional
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Transactional
@Repository
class ReadEmailServiceImpl(
    val emailRepository: EmailRepository,
    val patientRepository: PatientRepository,
    val readContactService: ReadContactService,
    val doctorRepository: DoctorRepository
) : ReadEmailService {
    private val logger = KotlinLogging.logger { }
    override suspend fun getByEmailId(emailId: EmailDto.EmailDtoId): EmailEntity? {
        return emailRepository.findById(emailId.value).orElse(null)
    }

    override suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): List<EmailEntity>? {
        val doctor = doctorRepository.findByIdOrNull(doctorId.value) ?: return null
        return doctor.emails.toList()
    }

    override suspend fun getPatientEmails(patientId: PatientDto.PatientDtoId): List<EmailEntity>? {
        return patientRepository.findByIdOrNull(patientId.value)?.emails?.toList()
    }

    override suspend fun getContactEmails(contactContactDtoId: ContactDto.ContactDtoId): List<EmailEntity>? {
        return readContactService.getByContactId(contactContactDtoId)?.email?.toList()
    }
}