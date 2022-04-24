package com.leftindust.mockingbird.event

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.graphql.types.GraphQLDate
import com.leftindust.mockingbird.graphql.types.GraphQLDayOfWeek

@GraphQLName("Recurrence")
data class GraphQLRecurrence(
    val startDate: GraphQLDate,
    val endDate: GraphQLDate,
    val daysOfWeek: List<GraphQLDayOfWeek>,
) {
    constructor(reoccurrence: Reoccurrence) : this(
        GraphQLDate(reoccurrence.startDate),
        GraphQLDate(reoccurrence.endDate),
        reoccurrence.days
    )
}