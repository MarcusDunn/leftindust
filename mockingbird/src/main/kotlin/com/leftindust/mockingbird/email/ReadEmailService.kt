package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.contact.ContactDto
import com.leftindust.mockingbird.patient.PatientDto
import kotlinx.coroutines.flow.Flow

interface ReadEmailService {
    suspend fun getByEmailId(emailId: EmailDto.Id): Email?
    suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): Flow<Email>?
    suspend fun getContactEmails(contactId: ContactDto.Id): Flow<Email>?
    suspend fun getPatientEmails(patientId: PatientDto.PatientDtoId): Flow<Email>?
}