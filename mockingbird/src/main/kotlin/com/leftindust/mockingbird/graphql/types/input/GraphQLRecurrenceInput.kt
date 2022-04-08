package com.leftindust.mockingbird.graphql.types.input

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.graphql.types.GraphQLDayOfWeek

@GraphQLName("RecurrenceInput")
data class GraphQLRecurrenceInput(
    val startDate: GraphQLDateInput,
    val endDate: GraphQLDateInput,
    val daysOfWeek: List<GraphQLDayOfWeek>,
)