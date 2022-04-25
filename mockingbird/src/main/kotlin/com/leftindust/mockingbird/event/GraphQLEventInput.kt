package com.leftindust.mockingbird.event

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.doctor.GraphQLDoctor
import com.leftindust.mockingbird.patient.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.GraphQLUtcTime


@GraphQLName("EventInput")
data class GraphQLEventInput(
    val title: String,
    val description: String? = null,
    @GraphQLDescription("UTC")
    val start: GraphQLUtcTime,
    @GraphQLDescription("UTC")
    val end: GraphQLUtcTime,
    val allDay: Boolean,
    val doctors: List<GraphQLDoctor.ID>? = emptyList(),
    val patients: List<GraphQLPatient.ID>? = emptyList(),
    val recurrence: GraphQLRecurrenceInput? = null
)


