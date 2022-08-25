package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.event.*
import com.leftindust.mockingbird.patient.PatientDto
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import java.util.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class EventQueryControllerUnitTest {
    @MockK
    private lateinit var readEventService: ReadEventServiceImpl

    private val eventToEventDtoConverter = EventToEventDtoConverter()

    @Test
    internal fun `check if eventIds on eventQueryController returns a list of queried users`() = runTest {
        val eventQueryController = EventQueryController(readEventService, eventToEventDtoConverter)

        val eventIds = listOf(
            EventDto.EventDtoId(UUID.randomUUID()),
            EventDto.EventDtoId(UUID.randomUUID()),
            EventDto.EventDtoId(UUID.randomUUID())
        )

        val event1 = mockk<Event>(relaxed = true)
        val event2 = mockk<Event>(relaxed = true)
        val event3 = mockk<Event>(relaxed = true)
        coEvery { readEventService.getByEventId(eventIds.first()) } returns event1
        coEvery { readEventService.getByEventId(eventIds[1]) } returns event2
        coEvery { readEventService.getByEventId(eventIds[2]) } returns event3

        val result = eventQueryController.eventsByIds(eventIds).toList()
        assertThat(result, Matchers.hasSize(3))
        assertThat(result.first(), Matchers.notNullValue())
        assertThat(result[1], Matchers.notNullValue())
        assertThat(result[2], Matchers.notNullValue())
    }

    @Test
    internal fun `check if eventIds returns a null`() = runTest {
        val eventQueryController = EventQueryController(readEventService, eventToEventDtoConverter)

        val eventIds = listOf(
            EventDto.EventDtoId(UUID.randomUUID()),
            EventDto.EventDtoId(UUID.randomUUID()),
            EventDto.EventDtoId(UUID.randomUUID())
        )
        val event1 = mockk<Event>(relaxed = true)
        val event2 = null
        val event3 = mockk<Event>(relaxed = true)
        coEvery { readEventService.getByEventId(eventIds.first()) } returns event1
        coEvery { readEventService.getByEventId(eventIds[1]) } returns event2
        coEvery { readEventService.getByEventId(eventIds[2]) } returns event3

        val result = eventQueryController.eventsByIds(eventIds).toList()
        assertThat(result, Matchers.hasSize(3))
        assertThat(result.first(), Matchers.notNullValue())
        assertThat(result[1], Matchers.nullValue())
        assertThat(result[2], Matchers.notNullValue())
    }

    @Test
    internal fun `check if queried eventsIds return null`() = runTest {
        val eventQueryController = EventQueryController(readEventService, eventToEventDtoConverter)

        val eventIds = listOf(
            EventDto.EventDtoId(UUID.randomUUID()),
            EventDto.EventDtoId(UUID.randomUUID()),
            EventDto.EventDtoId(UUID.randomUUID())
        )
        val event1 = null
        val event2 = null
        val event3 = null
        coEvery { readEventService.getByEventId(eventIds.first()) } returns event1
        coEvery { readEventService.getByEventId(eventIds[1]) } returns event2
        coEvery { readEventService.getByEventId(eventIds[2]) } returns event3

        val result = eventQueryController.eventsByIds(eventIds).toList()
        assertThat(result, Matchers.equalTo(listOf(null, null, null)))
    }

    @Test
    internal fun `check if eventsByDoctorId returns a value when eventService finds a list of events with the queried doctorId`() =
        runTest {
            val eventQueryController = EventQueryController(readEventService, eventToEventDtoConverter)
            val doctorDtoToDoctorId = DoctorDto.DoctorDtoId(UUID.randomUUID())

            val eventsListByDoctorId = mockk<List<Event>>(relaxed = true)
            coEvery { readEventService.getByDoctorId(doctorDtoToDoctorId) } returns eventsListByDoctorId

            val result = eventQueryController.eventsByDoctorId(doctorDtoToDoctorId)
            assertThat(result, Matchers.notNullValue())
        }

    @Test
    internal fun `check if eventsByDoctorId returns a null value when eventService finds a list of empty events by doctorId`() =
        runTest {
            val eventQueryController = EventQueryController(readEventService, eventToEventDtoConverter)
            val doctorDtoToDoctorId = DoctorDto.DoctorDtoId(UUID.randomUUID())

            coEvery { readEventService.getByDoctorId(doctorDtoToDoctorId) } returns null

            val result = eventQueryController.eventsByDoctorId(doctorDtoToDoctorId)
            assertThat(result, Matchers.nullValue())
        }

    @Test
    internal fun `check if eventsByPatientId returns a value when eventService finds a list of events with the queried patientId`() =
        runTest {
            val eventQueryController = EventQueryController(readEventService, eventToEventDtoConverter)
            val patientDtoToPatientId = PatientDto.PatientDtoId(UUID.randomUUID())

            val eventsListByPatientId = mockk<List<Event>>(relaxed = true)
            coEvery { readEventService.getByPatientId(patientDtoToPatientId) } returns eventsListByPatientId

            val result = eventQueryController.eventsByPatientId(patientDtoToPatientId)
            assertThat(result, Matchers.notNullValue())
        }

    @Test
    internal fun `check if eventsByPatientId returns a null value when eventService finds a list of empty events with the queried patientId`() =
        runTest {
            val eventQueryController = EventQueryController(readEventService, eventToEventDtoConverter)
            val patientDtoToPatientId = PatientDto.PatientDtoId(UUID.randomUUID())

            coEvery { readEventService.getByPatientId(patientDtoToPatientId) } returns null

            val result = eventQueryController.eventsByPatientId(patientDtoToPatientId)
            assertThat(result, Matchers.nullValue())
        }
}