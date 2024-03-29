package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.event.EventDto
import com.leftindust.mockingbird.event.EventFilter
import com.leftindust.mockingbird.event.EventFilterDto
import com.leftindust.mockingbird.event.FilterEventsService
import com.leftindust.mockingbird.event.ReadEventService
import com.leftindust.mockingbird.event.toEventDto
import dev.forkhandles.result4k.onFailure
import org.springframework.core.convert.converter.Converter
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class DoctorEventsQueryController(
    private val readEventsService: ReadEventService,
    private val filterEventService: FilterEventsService,
    private val eventFilterDtoToEventFilter: Converter<EventFilterDto, EventFilter>,
) {
    @SchemaMapping
    suspend fun events(doctorDto: DoctorDto, @Argument conditions: EventFilterDto): List<EventDto> {
        val events = readEventsService.getByDoctorId(doctorDto.id)
            ?: throw NullSubQueryException(doctorDto, ReadEventService::getByDoctorId)
        val eventFilter = eventFilterDtoToEventFilter.convert(conditions)
            ?: throw IllegalArgumentException("Invalid conditions $conditions")

        return filterEventService.filterEvents(events, eventFilter)
            .map { it.toEventDto().onFailure { throw it.reason.toMockingbirdException() } }
    }
}
