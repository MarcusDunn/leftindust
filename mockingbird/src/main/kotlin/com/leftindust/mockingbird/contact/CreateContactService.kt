package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.PersistenceError
import com.leftindust.mockingbird.patient.Patient
import dev.forkhandles.result4k.Result4k
import org.springframework.security.access.prepost.PreAuthorize
import java.util.*

@PreAuthorize("hasAuthority('CREATE_CONTACT')")
interface CreateContactService {
    suspend fun createContact(createContact: CreateContact, patientId: UUID): Result4k<Contact, PersistenceError>
    suspend fun createContact(createContact: CreateContact, patient: Patient): Contact
}

