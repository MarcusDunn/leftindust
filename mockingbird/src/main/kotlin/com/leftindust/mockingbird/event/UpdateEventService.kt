package com.leftindust.mockingbird.event

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('UPDATE_EVENT')")
interface UpdateEventService {
    suspend fun updateEvent(updateEvent: UpdateEvent): Event?
}