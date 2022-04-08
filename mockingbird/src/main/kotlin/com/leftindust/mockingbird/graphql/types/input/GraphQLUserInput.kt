package com.leftindust.mockingbird.graphql.types.input

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.expediagroup.graphql.generator.execution.OptionalInput
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLUserGroup

@GraphQLName("UserInput")
data class GraphQLUserInput(
    val uid: String,
    val nameInfo: GraphQLNameInfoInput,
    val group: GraphQLUserGroup.ID? = null,
    val doctor: GraphQLDoctor.ID? = null,
)

@GraphQLName("UserEditInput")
@GraphQLDescription("edits the user with the given uid, fields left unset will not be edited ")
data class GraphQLUserEditInput(
    val uid: String,
    val group: OptionalInput<GraphQLUserGroup.ID?> = OptionalInput.Undefined,
    val doctor: OptionalInput<GraphQLDoctor.ID?> = OptionalInput.Undefined,
)