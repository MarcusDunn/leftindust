package com.leftindust.mockingbird.dao.entity

import com.leftindust.mockingbird.graphql.types.GraphQLDayOfWeek
import com.leftindust.mockingbird.graphql.types.input.GraphQLRecurrenceInput
import java.time.LocalDate
import javax.persistence.*

@Embeddable
class Reoccurrence(
    val startDate: LocalDate,
    val endDate: LocalDate,
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    val days: List<GraphQLDayOfWeek>,
) {
    constructor(reoccurrence: GraphQLRecurrenceInput) : this(
        startDate = reoccurrence.startDate.toLocalDate(),
        endDate = reoccurrence.endDate.toLocalDate(),
        days = reoccurrence.daysOfWeek,
    )

    override fun toString(): String {
        return "Reoccurrence(startDate=$startDate, endDate=$endDate, days=$days)"
    }
}
