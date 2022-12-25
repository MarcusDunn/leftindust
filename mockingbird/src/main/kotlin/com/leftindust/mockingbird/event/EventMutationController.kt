package com.leftindust.mockingbird.event

import dev.forkhandles.result4k.onFailure
import mu.KotlinLogging
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class EventMutationController(
    private val createEventService: CreateEventService,
    private val updateEventService: UpdateEventService,
) {
    private val logger = KotlinLogging.logger { }

    @MutationMapping
    suspend fun addEvent(@Argument event: CreateEventDto): EventDto {
        val newEvent = createEventService.addEvent(event)
        return newEvent.toEventDto().onFailure { throw it.reason.toMockingbirdException() }
    }

    @MutationMapping
    suspend fun editEvent(@Argument event: UpdateEventDto): EventDto? {
        val updateEvent = updateEventService.updateEvent(event) ?: return null
        return updateEvent.toEventDto().onFailure { throw it.reason.toMockingbirdException() }
    }
}
