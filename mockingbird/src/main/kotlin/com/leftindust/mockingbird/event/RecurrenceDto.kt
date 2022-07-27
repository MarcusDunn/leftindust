package com.leftindust.mockingbird.event

import java.time.DayOfWeek
import java.time.LocalDate

data class RecurrenceDto(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val daysOfWeek: List<DayOfWeek>,
)