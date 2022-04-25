package com.leftindust.mockingbird.event

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.graphql.types.GraphQLDayOfWeek
import com.leftindust.mockingbird.graphql.types.input.GraphQLDateInput

@GraphQLName("RecurrenceInput")
data class GraphQLRecurrenceInput(
    val startDate: GraphQLDateInput,
    val endDate: GraphQLDateInput,
    val daysOfWeek: List<GraphQLDayOfWeek>,
)