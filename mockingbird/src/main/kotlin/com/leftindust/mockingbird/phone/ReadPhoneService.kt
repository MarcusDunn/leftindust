package com.leftindust.mockingbird.phone

import com.leftindust.mockingbird.contact.ContactDto
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.patient.PatientDto
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_PHONE')")
interface ReadPhoneService {

    suspend fun getByPhoneId(phoneId: PhoneDto.PhoneDtoId): Phone?

    suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): List<Phone>?

    suspend fun getByContactId(contactId: ContactDto.ContactDtoId): List<Phone>?

    suspend fun getPatientId(patientId: PatientDto.PatientDtoId): List<Phone>?
}
