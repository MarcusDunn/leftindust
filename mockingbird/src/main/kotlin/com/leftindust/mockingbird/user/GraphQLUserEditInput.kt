package com.leftindust.mockingbird.user

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.expediagroup.graphql.generator.execution.OptionalInput
import com.leftindust.mockingbird.doctor.GraphQLDoctor

@GraphQLName("UserEditInput")
@GraphQLDescription("edits the user with the given uid, fields left unset will not be edited ")
data class GraphQLUserEditInput(
    val uid: String,
    val group: OptionalInput<GraphQLUserGroup.ID?> = OptionalInput.Undefined,
    val doctor: OptionalInput<GraphQLDoctor.ID?> = OptionalInput.Undefined,
)