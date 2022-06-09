package com.leftindust.mockingbird.event

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import org.springframework.stereotype.Service

@Service
class FilterEventsServiceImpl : FilterEventsService {
    override suspend fun filterEvents(events: List<Event>, eventFilter: EventFilter): List<Event> {
        return events.filter { eventFilter.matches(it) }
    }
}