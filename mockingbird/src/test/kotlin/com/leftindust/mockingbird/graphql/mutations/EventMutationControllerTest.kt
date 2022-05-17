package com.leftindust.mockingbird.graphql.mutations

import com.expediagroup.graphql.generator.execution.OptionalInput
import com.leftindust.mockingbird.event.EventService
import com.leftindust.mockingbird.event.Event
import com.leftindust.mockingbird.event.EventMutationController
import com.leftindust.mockingbird.event.EventDto
import com.leftindust.mockingbird.event.UpdateEventDto
import com.leftindust.mockingbird.util.EntityStore
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class EventMutationControllerTest {
    private val eventService = mockk<EventService>()

    @AfterEach
    internal fun tearDown() {
        confirmVerified(eventService)
    }

    @Test
    fun addEvent() {
        val eventID = UUID.randomUUID()

        val mockkEvent = mockk<Event>(relaxed = true) {
            every { id } returns eventID
        }

        every { eventService.addEvent(any(), any()) } returns mockkEvent

        val eventMutationController = EventMutationController(eventService)
        val event = EntityStore.graphQLEventInput("EventMutationTest.addEvent")
        val result = runBlocking {
            eventMutationController.addEvent(event, MockDataFetchingEnvironment.withDummyMediqToken)
        }

        verifyAll {
            mockkEvent.id
            mockkEvent.title
            mockkEvent.description
            mockkEvent.startTime
            mockkEvent.endTime
            mockkEvent.allDay
            mockkEvent.reoccurrence
            eventService.addEvent(any(), any())
        }

        confirmVerified(mockkEvent)

        assertEquals(EventDto(mockkEvent), result)

    }

    @Test
    internal fun `edit event`() {
        val eventID = UUID.randomUUID()

        val eventMutationController = EventMutationController(eventService)

        val mockkEvent = mockk<Event>(relaxed = true) {
            every { id } returns eventID
        }

        every { eventService.editEvent(any(), any()) } returns mockkEvent

        val result = runBlocking {
            eventMutationController.editEvent(
                event = UpdateEventDto(
                    eid = EventDto.EventDtoId(eventID),
                    description = OptionalInput.Defined("new descr")
                ),
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }


        verifyAll {
            mockkEvent.id
            mockkEvent.title
            mockkEvent.description
            mockkEvent.startTime
            mockkEvent.endTime
            mockkEvent.allDay
            mockkEvent.reoccurrence
            eventService.editEvent(any(), any())
        }

        confirmVerified(mockkEvent)

        assertEquals(EventDto(mockkEvent), result)

    }

    @Test
    internal fun `edit event with recurrence`() {
        val eventID = UUID.randomUUID()

        val editedEvent = mockk<Event>(relaxed = true) {
            every { id } returns eventID
        }

        every { eventService.editRecurringEvent(any(), any(), any()) } returns editedEvent

        val eventMutationController = EventMutationController(eventService)

        val result = runBlocking {
            eventMutationController.editRecurringEvent(
                event = mockk(relaxed = true),
                recurrenceSettings = mockk(),
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }


        verifyAll {
            eventService.editRecurringEvent(any(), any(), any())
            editedEvent.id
            editedEvent.reoccurrence
            editedEvent.title
            editedEvent.description
            editedEvent.startTime
            editedEvent.endTime
            editedEvent.allDay
        }

        confirmVerified(editedEvent)

        assertEquals(EventDto(editedEvent), result)
    }
}