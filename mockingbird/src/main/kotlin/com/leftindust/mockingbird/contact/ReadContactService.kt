package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.patient.PatientDto
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_CONTACT')")
interface ReadContactService {
    suspend fun getByPatientId(patientDtoId: PatientDto.PatientDtoId): List<Contact>?

    suspend fun getByContactId(contactDtoId: ContactDto.ContactDtoId): Contact?
}
