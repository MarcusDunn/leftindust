package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.EventDao
import com.leftindust.mockingbird.dao.entity.Event
import com.leftindust.mockingbird.dao.entity.Patient
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLEvent
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.GraphQLUtcTime
import com.leftindust.mockingbird.graphql.types.input.GraphQLTimeRangeInput
import com.leftindust.mockingbird.util.makeUUID
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.Duration
import java.time.Instant
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class EventQueryTest {
    private val eventDao = mockk<EventDao>()

    @Test
    internal fun getByDoctor() {
        val doctorID = UUID.randomUUID()


        val events = listOf<Event>(mockk(relaxed = true), mockk(relaxed = true))

        every { eventDao.getByDoctor(GraphQLDoctor.ID(doctorID), any()) } returns events

        val eventQuery = EventQuery(eventDao)

        val graphQLAuthContext = mockk<GraphQLAuthContext> {
            every { mediqAuthToken } returns mockk()
        }

        val result = runBlocking {
            eventQuery.eventsByDoctor(
                doctors = listOf(GraphQLDoctor.ID(doctorID)),
                graphQLAuthContext = graphQLAuthContext
            )
        }

        val expected = events.map { GraphQLEvent(it, graphQLAuthContext) }

        assertEquals(expected, result)
    }

    @Test
    internal fun getByPatient() {
        val patientID = UUID.randomUUID()
        val eventID = UUID.randomUUID()

        val expected = mockk<Patient> {
            every { id } returns patientID
            every { events } returns mutableSetOf(mockk(relaxed = true) {
                every { id } returns eventID
            })

        }
        every { eventDao.getPatientEvents(GraphQLPatient.ID(patientID), any()) } returns expected.events

        val eventQuery = EventQuery(eventDao)

        val graphQLAuthContext = mockk<GraphQLAuthContext> {
            every { mediqAuthToken } returns mockk()
        }
        val result =
            runBlocking {
                eventQuery.eventsByPatient(
                    patients = listOf(GraphQLPatient.ID(patientID)),
                    graphQLAuthContext = graphQLAuthContext
                )
            }

        assertEquals(expected.events.map {
            GraphQLEvent(
                event = it,
                authContext = graphQLAuthContext
            )
        }, result)
    }

    @Test
    fun eventsByIds(): Unit = runBlocking {
        val eventQuery = EventQuery(eventDao)
        val graphQLAuthContext = mockk<GraphQLAuthContext> {
            every { mediqAuthToken } returns mockk()
        }

        val event1 = mockk<Event>(relaxed = true)
        val event2 = mockk<Event>(relaxed = true)

        val eventId1 = GraphQLEvent.ID(makeUUID("1"))
        val eventId2 = GraphQLEvent.ID(makeUUID("2"))

        every { eventDao.getById(eventId1, graphQLAuthContext.mediqAuthToken) } returns event1
        every { eventDao.getById(eventId2, graphQLAuthContext.mediqAuthToken) } returns event2

        val result = eventQuery.eventsByIds(listOf(eventId1, eventId2), graphQLAuthContext)
        val expected = listOf(event1, event2).map { GraphQLEvent(it, graphQLAuthContext) }
        assertEquals(expected, result)

    }


    @Test
    fun eventsByRange(): Unit = runBlocking {
        val eventQuery = EventQuery(eventDao)

        val graphQLAuthContext = mockk<GraphQLAuthContext> {
            every { mediqAuthToken } returns mockk()
        }

        val rangeInput = GraphQLTimeRangeInput(
            start = GraphQLUtcTime(Instant.now()),
            end = GraphQLUtcTime(Instant.now() + Duration.ofDays(1))
        )

        val expected = listOf<Event>(mockk(relaxed = true), mockk(relaxed = true))

        every { eventDao.getBetween(rangeInput, graphQLAuthContext.mediqAuthToken) } returns expected

        val result = eventQuery.eventsByTimeRange(rangeInput, graphQLAuthContext)

        assertEquals(expected.map { GraphQLEvent(it, graphQLAuthContext) }, result)

        verify(exactly = 1) { eventDao.getBetween(rangeInput, graphQLAuthContext.mediqAuthToken) }
    }

    @Test
    fun eventsByDoctorsAndPatients(): Unit = runBlocking {
        val eventQuery = EventQuery(eventDao)

        val graphQLAuthContext = mockk<GraphQLAuthContext> {
            every { mediqAuthToken } returns mockk()
        }

        val events = listOf<Event>(mockk(relaxed = true), mockk(relaxed = true))

        val pid = GraphQLPatient.ID(makeUUID("1"))
        every { eventDao.getPatientEvents(pid, graphQLAuthContext.mediqAuthToken) } returns events

        val did = GraphQLDoctor.ID(makeUUID("2"))
        every { eventDao.getByDoctor(did, graphQLAuthContext.mediqAuthToken) } returns events

        val result = eventQuery.eventsByDoctorsAndPatients(listOf(pid), listOf(did), graphQLAuthContext)
        assertEquals(events.map { GraphQLEvent(it, graphQLAuthContext) }, result)

    }
}