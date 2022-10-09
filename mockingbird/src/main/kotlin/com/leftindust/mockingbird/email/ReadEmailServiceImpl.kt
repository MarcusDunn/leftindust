package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.InfallibleConverter
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
    val doctorRepository: DoctorRepository,
    val emailEntityToEmailConverter: InfallibleConverter<EmailEntity, Email>,
) : ReadEmailService {
    private val logger = KotlinLogging.logger { }
    override suspend fun getByEmailId(emailId: EmailDto.EmailDtoId): Email? {
        val emailEntity = emailRepository.findByIdOrNull(emailId.value)
            ?: return null
        return emailEntityToEmailConverter.convert(emailEntity)
    }

    override suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): List<Email>? {
        val doctor = doctorRepository.findByIdOrNull(doctorId.value)
            ?: return null
        return doctor.emails.map { emailEntityToEmailConverter.convert(it) }
    }

    override suspend fun getPatientEmails(patientId: PatientDto.PatientDtoId): List<Email>? {
        val patientEntity = patientRepository.findByIdOrNull(patientId.value)
            ?: return null
        return patientEntity.emails.map { emailEntityToEmailConverter.convert(it) }
    }

    override suspend fun getContactEmails(contactContactDtoId: ContactDto.ContactDtoId): List<Email>? {
        val contactEntity = readContactService.getByContactId(contactContactDtoId)
            ?: return null
        return contactEntity.email.map { emailEntityToEmailConverter.convert(it) }
    }
}