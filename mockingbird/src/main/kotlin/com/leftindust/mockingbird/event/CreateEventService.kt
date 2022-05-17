package com.leftindust.mockingbird.event

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('CREATE_EVENT')")
interface CreateEventService {
    suspend fun addEvent(createEvent: CreateEvent): Event
}