package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.dao.entity.Reoccurrence

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