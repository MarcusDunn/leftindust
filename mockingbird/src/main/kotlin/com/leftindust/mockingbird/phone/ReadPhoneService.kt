package com.leftindust.mockingbird.phone

import com.leftindust.mockingbird.contact.ContactDto
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.patient.PatientDto
import kotlinx.coroutines.flow.Flow
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_PHONE')")
interface ReadPhoneService {
    fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): Flow<Phone>?

    fun getByContactId(contactId: ContactDto.Id): Flow<Phone>?

    fun getPatientId(patientId: PatientDto.PatientDtoId): Flow<Phone>?
}
