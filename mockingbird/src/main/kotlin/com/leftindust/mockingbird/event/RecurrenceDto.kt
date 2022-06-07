package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.graphql.types.LocalDateDto
import java.time.DayOfWeek

data class RecurrenceDto(
    val startDate: LocalDateDto,
    val endDate: LocalDateDto,
    val daysOfWeek: List<DayOfWeek>,
)