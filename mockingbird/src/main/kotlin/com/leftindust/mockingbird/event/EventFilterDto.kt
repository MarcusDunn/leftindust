package com.leftindust.mockingbird.event

data class EventFilterDto(
    val before: ZonedDateTimeDto,
    val after: ZonedDateTimeDto,
)
