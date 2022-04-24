package com.leftindust.mockingbird.graphql.mutations

import com.expediagroup.graphql.generator.execution.OptionalInput
import com.leftindust.mockingbird.event.EventDao
import com.leftindust.mockingbird.event.Event
import com.leftindust.mockingbird.event.EventMutation
import com.leftindust.mockingbird.event.GraphQLEvent
import com.leftindust.mockingbird.event.GraphQLEventEditInput
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

internal class EventMutationTest {
    private val eventDao = mockk<EventDao>()

    @AfterEach
    internal fun tearDown() {
        confirmVerified(eventDao)
    }

    @Test
    fun addEvent() {
        val eventID = UUID.randomUUID()

        val mockkEvent = mockk<Event>(relaxed = true) {
            every { id } returns eventID
        }

        every { eventDao.addEvent(any(), any()) } returns mockkEvent

        val eventMutation = EventMutation(eventDao)
        val event = EntityStore.graphQLEventInput("EventMutationTest.addEvent")
        val result = runBlocking {
            eventMutation.addEvent(event, MockDataFetchingEnvironment.withDummyMediqToken)
        }

        verifyAll {
            mockkEvent.id
            mockkEvent.title
            mockkEvent.description
            mockkEvent.startTime
            mockkEvent.endTime
            mockkEvent.allDay
            mockkEvent.reoccurrence
            eventDao.addEvent(any(), any())
        }

        confirmVerified(mockkEvent)

        assertEquals(GraphQLEvent(mockkEvent), result)

    }

    @Test
    internal fun `edit event`() {
        val eventID = UUID.randomUUID()

        val eventMutation = EventMutation(eventDao)

        val mockkEvent = mockk<Event>(relaxed = true) {
            every { id } returns eventID
        }

        every { eventDao.editEvent(any(), any()) } returns mockkEvent

        val result = runBlocking {
            eventMutation.editEvent(
                event = GraphQLEventEditInput(
                    eid = GraphQLEvent.ID(eventID),
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
            eventDao.editEvent(any(), any())
        }

        confirmVerified(mockkEvent)

        assertEquals(GraphQLEvent(mockkEvent), result)

    }

    @Test
    internal fun `edit event with recurrence`() {
        val eventID = UUID.randomUUID()

        val editedEvent = mockk<Event>(relaxed = true) {
            every { id } returns eventID
        }

        every { eventDao.editRecurringEvent(any(), any(), any()) } returns editedEvent

        val eventMutation = EventMutation(eventDao)

        val result = runBlocking {
            eventMutation.editRecurringEvent(
                event = mockk(relaxed = true),
                recurrenceSettings = mockk(),
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }


        verifyAll {
            eventDao.editRecurringEvent(any(), any(), any())
            editedEvent.id
            editedEvent.reoccurrence
            editedEvent.title
            editedEvent.description
            editedEvent.startTime
            editedEvent.endTime
            editedEvent.allDay
        }

        confirmVerified(editedEvent)

        assertEquals(GraphQLEvent(editedEvent), result)
    }
}