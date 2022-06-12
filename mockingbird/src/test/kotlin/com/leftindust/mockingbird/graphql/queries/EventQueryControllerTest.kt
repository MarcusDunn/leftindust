package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.event.*
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
        val eventQueryController = EventQueryController(readEventService,eventToEventDtoConverter)

        val eventIds = listOf(
            EventDto.EventDtoId(UUID.randomUUID()),
            EventDto.EventDtoId(UUID.randomUUID()),
            EventDto.EventDtoId(UUID.randomUUID())
        )

        val event1 = mockk<Event>(relaxed = true )
        val event2 = mockk<Event>(relaxed = true )
        val event3 = mockk<Event>(relaxed = true )
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
        val eventQueryController = EventQueryController(readEventService,eventToEventDtoConverter)

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
        assertThat( result, Matchers.hasSize(3))
        assertThat(result.first(), Matchers.notNullValue())
        assertThat(result[1], Matchers.equalTo(null))
        assertThat(result[2], Matchers.notNullValue())
    }

    @Test
    internal fun `check if queried eventsIds return null`() = runTest {
        val eventQueryController = EventQueryController(readEventService,eventToEventDtoConverter)

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
        assertThat( result, Matchers.hasSize(3))
        assertThat( result.first(), Matchers.equalTo(null))
        assertThat(result[1], Matchers.equalTo(null))
        assertThat(result[2], Matchers.equalTo(null))
    }

    @Test
    internal fun `check if eventsByDoctorId returns a doctorId`() = runTest {
        val eventQueryController = EventQueryController(readEventService,eventToEventDtoConverter)
        val eventsByDoctorId = DoctorDto.DoctorDtoId(UUID.randomUUID())

        val doctorId = mockk<List<Event>>(relaxed = true)
        coEvery { readEventService.getByDoctorId(eventsByDoctorId) } returns doctorId

        val result = eventQueryController.eventsByDoctorId(eventsByDoctorId)
        assertThat( result, Matchers.notNullValue())
    }

    @Test
    internal fun `check if queried eventsByDoctorId returns null` () = runTest {
        val eventQueryController = EventQueryController(readEventService,eventToEventDtoConverter)
        val eventsByDoctorId = DoctorDto.DoctorDtoId(UUID.randomUUID())

        val doctorId = null
        coEvery { readEventService.getByDoctorId(eventsByDoctorId) } returns doctorId

        val result = eventQueryController.eventsByDoctorId(eventsByDoctorId)
        assertThat( result, Matchers.equalTo(null))
    }
}