package com.leftindust.mockingbird.event

interface FilterEventsService {
    suspend fun filterEvents(events: List<Event>, eventFilter: EventFilter): List<Event>
}

