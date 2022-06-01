package com.leftindust.mockingbird.event

import kotlinx.coroutines.flow.Flow

interface FilterEventsService {
    suspend fun filterEvents(events: Flow<Event>, eventFilter: EventFilter): Flow<Event>
}

