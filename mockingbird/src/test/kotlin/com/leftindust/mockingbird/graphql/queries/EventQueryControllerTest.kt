package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.event.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import java.util.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
internal class EventQueryControllerUnitTest () {
    private val readEventService = mockk<ReadEventServiceImpl>()
    private val eventToEventDtoConverter = EventToEventDtoConverter()
    private val eventQueryController = EventQueryController(readEventService,eventToEventDtoConverter)

    @Test
    internal fun `check if eventIds on eventQueryController returns a list of queried users`() = runTest {
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
}