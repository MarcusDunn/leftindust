package com.leftindust.mockingbird.phone

import com.leftindust.mockingbird.contact.ContactDto
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.patient.PatientDto
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_PHONE')")
interface ReadPhoneService {
    fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): List<Phone>?

    fun getByContactId(contactContactDtoId: ContactDto.ContactDtoId): List<Phone>?

    fun getPatientId(patientId: PatientDto.PatientDtoId): List<Phone>?
}
