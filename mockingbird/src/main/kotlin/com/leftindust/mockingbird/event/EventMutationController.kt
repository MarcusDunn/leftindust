package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.InfallibleConverter
import mu.KotlinLogging
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class EventMutationController(
    private val createEventService: CreateEventService,
    private val eventToEventDtoConverter: InfallibleConverter<Event, EventDto>,
    private val updateEventService: UpdateEventService,
) {
    private val logger = KotlinLogging.logger { }

    @MutationMapping
    suspend fun addEvent(@Argument event: CreateEventDto): EventDto {
        val newEvent = createEventService.addEvent(event)
        return eventToEventDtoConverter.convert(newEvent)
    }

    @MutationMapping
    suspend fun editEvent(@Argument event: UpdateEventDto): EventDto? {
        val updateEvent = updateEventService.updateEvent(event) ?: return null
        return eventToEventDtoConverter.convert(updateEvent)
    }
}
