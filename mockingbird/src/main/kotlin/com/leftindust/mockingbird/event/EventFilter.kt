package com.leftindust.mockingbird.event

import java.time.ZonedDateTime

data class EventFilter(val before: ZonedDateTime?, val after: ZonedDateTime?) {
    fun matches(event: Event): Boolean = matchesBefore(event) && matchesAfter(event)

    private fun matchesAfter(event: Event): Boolean = TODO()

    private fun matchesBefore(event: Event): Boolean = TODO()
}