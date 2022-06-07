package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.NullFromServiceMessage
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.contact.ContactDto
import com.leftindust.mockingbird.contact.ReadContactService
import com.leftindust.mockingbird.doctor.ReadDoctorService
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import javax.transaction.Transactional
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Transactional
@Repository
class ReadEmailServiceImpl(
    val emailRepository: EmailRepository,
    val readDoctorService: ReadDoctorService,
    val readPatientService: ReadPatientService,
    val readContactService: ReadContactService,
) : ReadEmailService {
    private val logger = LoggerFactory.getLogger(ReadEmailServiceImpl::class.java)
    override suspend fun getByEmailId(emailId: EmailDto.Id): Email? {
        return emailRepository.findById(emailId.value).orElseGet(null) ?: run {
            logger.trace(NullFromServiceMessage(ReadEmailServiceImpl::getByEmailId, "${EmailRepository::class.simpleName}.${EmailRepository::findById.name}, returned null").toString())
            null
        }
    }

    override suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): Flow<Email>? {
        return readDoctorService.getByDoctorId(doctorId)?.emails?.asFlow() ?: run {
            logger.trace(NullFromServiceMessage(ReadEmailServiceImpl::getByDoctorId, "${ReadDoctorService::class.simpleName}.${ReadDoctorService::getByDoctorId.name} returned null").toString())
            null
        }
    }

    override suspend fun getPatientEmails(patientId: PatientDto.PatientDtoId): Flow<Email>? {
        return readPatientService.getByPatientId(patientId)?.emails?.asFlow() ?: run {
            logger.trace(NullFromServiceMessage(ReadEmailServiceImpl::getPatientEmails, "${ReadPatientService::class.simpleName}.${ReadPatientService::getByPatientId.name} returned null").toString())
            null
        }
    }

    override suspend fun getContactEmails(contactId: ContactDto.Id): Flow<Email>? {
        return readContactService.getByContactId(contactId)?.email?.asFlow() ?: run {
            logger.trace(NullFromServiceMessage(ReadEmailServiceImpl::getContactEmails, "${ReadContactService::class.simpleName}.${ReadContactService::getByContactId.name} returned null").toString())
            null
        }
    }
}