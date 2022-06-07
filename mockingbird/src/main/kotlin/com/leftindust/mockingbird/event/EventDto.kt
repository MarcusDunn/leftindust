package com.leftindust.mockingbird.event


import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import java.util.UUID

data class EventDto(
    override val id: EventDtoId,
    val iCal: String,
) : AbstractGraphQLDto<EventDto.EventDtoId>() {
    data class EventDtoId(override val value: UUID) : GraphQLID<UUID>

}