package com.leftindust.mockingbird.graphql.types.input

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.expediagroup.graphql.generator.execution.OptionalInput

@GraphQLName("NameInput")
data class GraphQLNameInfoInput(
    val firstName: String,
    val middleName: String? = null,
    val lastName: String,
)

@GraphQLName("NameEditInput")
data class GraphQLNameInfoEditInput(
    @GraphQLDescription("setting firstName to null will have no effect on updates")
    val firstName: String? = null,
    @GraphQLDescription("setting middleName to null will remove a prior middleName")
    val middleName: OptionalInput<String> = OptionalInput.Undefined,
    @GraphQLDescription("setting lastName to null will have no effect on updates")
    val lastName: String? = null,
)