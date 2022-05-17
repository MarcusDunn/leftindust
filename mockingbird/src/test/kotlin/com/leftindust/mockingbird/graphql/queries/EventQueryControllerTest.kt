package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.event.EventService
import com.leftindust.mockingbird.event.Event
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.event.EventQueryController
import com.leftindust.mockingbird.event.EventDto
import com.leftindust.mockingbird.patient.PatientDto
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

internal class EventQueryControllerTest {
    private val eventService = mockk<EventService>()

    @Test
    internal fun getByDoctor() {
        val doctorID = UUID.randomUUID()

        val events = listOf<Event>(mockk(relaxed = true), mockk(relaxed = true))

        every { eventService.getByDoctor(DoctorDto.DoctorDtoId(doctorID), any()) } returns events

        val eventQueryController = EventQueryController(eventService)

        val result = runBlocking {
            eventQueryController.eventsByDoctor(
                doctors = listOf(DoctorDto.DoctorDtoId(doctorID)),
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }

        val expected = events.map { EventDto(it) }

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
        every { eventService.getPatientEvents(PatientDto.PatientDtoId(patientID), any()) } returns expected.events

        val eventQueryController = EventQueryController(eventService)

        val result = runBlocking {
            eventQueryController.eventsByPatient(
                patients = listOf(PatientDto.PatientDtoId(patientID)),
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }

        assertEquals(expected.events.map(::EventDto), result)
    }

    @Test
    fun eventsByIds(): Unit = runBlocking {
        val eventQueryController = EventQueryController(eventService)

        val event1 = mockk<Event>(relaxed = true)
        val event2 = mockk<Event>(relaxed = true)

        val eventEventDtoId1 = EventDto.EventDtoId(makeUUID("1"))
        val eventEventDtoId2 = EventDto.EventDtoId(makeUUID("2"))

        every { eventService.getByEventId(eventEventDtoId1, any()) } returns event1
        every { eventService.getByEventId(eventEventDtoId2, any()) } returns event2

        val result = eventQueryController.eventsByIds(listOf(eventEventDtoId1, eventEventDtoId2), MockDataFetchingEnvironment.withDummyMediqToken)
        val expected = listOf(event1, event2).map { EventDto(it) }
        assertEquals(expected, result)

    }


    @Test
    fun eventsByRange(): Unit = runBlocking {
        val eventQueryController = EventQueryController(eventService)

        val rangeInput = GraphQLTimeRangeInput(
            start = GraphQLUtcTime(Instant.now()),
            end = GraphQLUtcTime(Instant.now() + Duration.ofDays(1))
        )

        val expected = listOf<Event>(mockk(relaxed = true), mockk(relaxed = true))

        every { eventService.getBetween(rangeInput, any()) } returns expected

        val result = eventQueryController.eventsByTimeRange(rangeInput, MockDataFetchingEnvironment.withDummyMediqToken)

        assertEquals(expected.map { EventDto(it) }, result)

        verify(exactly = 1) { eventService.getBetween(rangeInput, any()) }
    }

    @Test
    fun eventsByDoctorsAndPatients(): Unit = runBlocking {
        val eventQueryController = EventQueryController(eventService)

        val events = listOf<Event>(mockk(relaxed = true), mockk(relaxed = true))

        val pid = PatientDto.PatientDtoId(makeUUID("1"))
        every { eventService.getPatientEvents(pid, any()) } returns events

        val did = DoctorDto.DoctorDtoId(makeUUID("2"))
        every { eventService.getByDoctor(did, any()) } returns events

        val result = eventQueryController.eventsByDoctorsAndPatients(
            patientIds = listOf(pid),
            doctorIds = listOf(did),
            dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
        )
        assertEquals(events.map(::EventDto), result)

    }
}