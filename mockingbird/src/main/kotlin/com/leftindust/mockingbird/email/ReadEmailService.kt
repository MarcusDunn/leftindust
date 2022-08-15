package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.contact.ContactDto
import com.leftindust.mockingbird.patient.PatientDto

interface ReadEmailService {
    suspend fun getByEmailId(emailId: EmailDto.EmailDtoId): Email?
    suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): List<Email>?
    suspend fun getContactEmails(contactContactDtoId: ContactDto.ContactDtoId): List<Email>?
    suspend fun getPatientEmails(patientId: PatientDto.PatientDtoId): List<Email>?
}