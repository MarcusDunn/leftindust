package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.dao.EventDao
import com.leftindust.mockingbird.dao.entity.Event
import com.leftindust.mockingbird.dao.entity.Patient
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLEvent
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.GraphQLUtcTime
import com.leftindust.mockingbird.graphql.types.input.GraphQLTimeRangeInput
import com.leftindust.mockingbird.util.makeUUID
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.Instant
import java.util.*

internal class EventQueryTest {
    private val eventDao = mockk<EventDao>()

    @Test
    internal fun getByDoctor() {
        val doctorID = UUID.randomUUID()

        val events = listOf<Event>(mockk(relaxed = true), mockk(relaxed = true))

        every { eventDao.getByDoctor(GraphQLDoctor.ID(doctorID), any()) } returns events

        val eventQuery = EventQuery(eventDao)

        val result = runBlocking {
            eventQuery.eventsByDoctor(
                doctors = listOf(GraphQLDoctor.ID(doctorID)),
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }

        val expected = events.map { GraphQLEvent(it) }

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

        val result = runBlocking {
            eventQuery.eventsByPatient(
                patients = listOf(GraphQLPatient.ID(patientID)),
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }

        assertEquals(expected.events.map(::GraphQLEvent), result)
    }

    @Test
    fun eventsByIds(): Unit = runBlocking {
        val eventQuery = EventQuery(eventDao)

        val event1 = mockk<Event>(relaxed = true)
        val event2 = mockk<Event>(relaxed = true)

        val eventId1 = GraphQLEvent.ID(makeUUID("1"))
        val eventId2 = GraphQLEvent.ID(makeUUID("2"))

        every { eventDao.getById(eventId1, any()) } returns event1
        every { eventDao.getById(eventId2, any()) } returns event2

        val result = eventQuery.eventsByIds(listOf(eventId1, eventId2), MockDataFetchingEnvironment.withDummyMediqToken)
        val expected = listOf(event1, event2).map { GraphQLEvent(it) }
        assertEquals(expected, result)

    }


    @Test
    fun eventsByRange(): Unit = runBlocking {
        val eventQuery = EventQuery(eventDao)

        val rangeInput = GraphQLTimeRangeInput(
            start = GraphQLUtcTime(Instant.now()),
            end = GraphQLUtcTime(Instant.now() + Duration.ofDays(1))
        )

        val expected = listOf<Event>(mockk(relaxed = true), mockk(relaxed = true))

        every { eventDao.getBetween(rangeInput, any()) } returns expected

        val result = eventQuery.eventsByTimeRange(rangeInput, MockDataFetchingEnvironment.withDummyMediqToken)

        assertEquals(expected.map { GraphQLEvent(it) }, result)

        verify(exactly = 1) { eventDao.getBetween(rangeInput, any()) }
    }

    @Test
    fun eventsByDoctorsAndPatients(): Unit = runBlocking {
        val eventQuery = EventQuery(eventDao)

        val events = listOf<Event>(mockk(relaxed = true), mockk(relaxed = true))

        val pid = GraphQLPatient.ID(makeUUID("1"))
        every { eventDao.getPatientEvents(pid, any()) } returns events

        val did = GraphQLDoctor.ID(makeUUID("2"))
        every { eventDao.getByDoctor(did, any()) } returns events

        val result = eventQuery.eventsByDoctorsAndPatients(
            patients = listOf(pid),
            doctors = listOf(did),
            dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
        )
        assertEquals(events.map(::GraphQLEvent), result)

    }
}