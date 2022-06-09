package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.contact.ContactDto
import com.leftindust.mockingbird.contact.ReadContactService
import com.leftindust.mockingbird.doctor.ReadDoctorService
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import javax.transaction.Transactional
import mu.KotlinLogging
import org.springframework.stereotype.Repository

@Transactional
@Repository
class ReadEmailServiceImpl(
    val emailRepository: EmailRepository,
    val readDoctorService: ReadDoctorService,
    val readPatientService: ReadPatientService,
    val readContactService: ReadContactService,
) : ReadEmailService {
    private val logger = KotlinLogging.logger { }
    override suspend fun getByEmailId(emailId: EmailDto.Id): Email? {
        return emailRepository.findById(emailId.value).orElse(null)
    }

    override suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): List<Email>? {
        val doctor = readDoctorService.getByDoctorId(doctorId) ?: return null
        return doctor.emails.toList()
    }

    override suspend fun getPatientEmails(patientId: PatientDto.PatientDtoId): List<Email>? {
        return readPatientService.getByPatientId(patientId)?.emails?.toList()
    }

    override suspend fun getContactEmails(contactContactDtoId: ContactDto.ContactDtoId): List<Email>? {
        return readContactService.getByContactId(contactContactDtoId)?.email?.toList()
    }
}