package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.MockingbirdException
import com.leftindust.mockingbird.patient.CreateContactPatient
import dev.forkhandles.result4k.Result4k
import org.springframework.security.access.prepost.PreAuthorize


@PreAuthorize("hasAuthority('CREATE_CONTACT')")
interface CreateContactService {
    suspend fun createContact(createContact: CreateContactPatient): Result4k<Contact, MockingbirdException>
}
