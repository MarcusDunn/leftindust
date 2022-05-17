package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.patient.PatientDto
import kotlinx.coroutines.flow.Flow
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_CONTACT')")
interface ReadContactService {
    suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): Flow<Contact>?

    suspend fun getByContactId(contactId: ContactDto.Id): Contact?
}
