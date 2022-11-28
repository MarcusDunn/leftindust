package com.leftindust.mockingbird.event

import java.time.DayOfWeek
import java.time.LocalDate
import jakarta.persistence.*

@Embeddable
class Reoccurrence(
    val startDate: LocalDate,
    val endDate: LocalDate,
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    val days: List<DayOfWeek>,
)