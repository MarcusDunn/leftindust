package com.leftindust.mockingbird.contact

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('CREATE_CONTACT')")
interface CreateContactService {
    suspend fun createContact(createContact: CreateContact): Contact
}
