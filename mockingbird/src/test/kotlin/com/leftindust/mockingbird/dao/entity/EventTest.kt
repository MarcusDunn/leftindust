package com.leftindust.mockingbird.dao.entity

import com.expediagroup.graphql.generator.execution.OptionalInput
import com.leftindust.mockingbird.event.EventDto
import com.leftindust.mockingbird.event.UpdateEventDto
import com.leftindust.mockingbird.util.EntityStore
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class EventTest {

    @Test
    fun update() {
        val eventID = UUID.randomUUID()
        val event = EntityStore.event("EventTest.update")
            .apply { id = eventID }
        event.update(
            UpdateEventDto(
                eid = EventDto.EventDtoId(eventID),
                description = OptionalInput.Defined("new fancy description"),
                allDay = true,
                end = OptionalInput.Defined(null)
            ),
            emptySet(),
            emptySet()
        )
        assertEquals("new fancy description", event.description)
        assertEquals(true, event.allDay)
        assertEquals(event.startTime, event.startTime)
        assertEquals(null, event.endTime)
    }
}