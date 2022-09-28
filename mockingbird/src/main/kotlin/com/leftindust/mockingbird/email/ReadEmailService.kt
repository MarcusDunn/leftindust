package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.contact.ContactDto
import com.leftindust.mockingbird.patient.PatientDto

interface ReadEmailService {
    suspend fun getByEmailId(emailId: EmailDto.EmailDtoId): EmailEntity?
    suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): List<EmailEntity>?
    suspend fun getContactEmails(contactContactDtoId: ContactDto.ContactDtoId): List<EmailEntity>?
    suspend fun getPatientEmails(patientId: PatientDto.PatientDtoId): List<EmailEntity>?
}