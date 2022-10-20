package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.PersistenceError
import dev.forkhandles.result4k.Result4k
import org.springframework.security.access.prepost.PreAuthorize
import java.util.*

@PreAuthorize("hasAuthority('CREATE_CONTACT')")
interface CreateContactService {
    suspend fun createContact(createContact: CreateContactPatient): Result4k<Contact, PersistenceError>
}
