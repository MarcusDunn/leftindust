package com.leftindust.mockingbird.event

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.graphql.types.input.GraphQLDateInput

@GraphQLName("RecurrenceEditSettings")
@GraphQLDescription(
    """the date range that the edits will effect the reoccurring event. This allows things such as editing a 
            single event of a reoccurring event or leaving the past events untouched but editing future ones"""
)
data class GraphQLRecurrenceEditSettings(
    @GraphQLDescription("the start of events that the edit should take place on")
    val editStart: GraphQLDateInput,
    @GraphQLDescription("the end of the events that the edit should take place on")
    val editEnd: GraphQLDateInput,
)