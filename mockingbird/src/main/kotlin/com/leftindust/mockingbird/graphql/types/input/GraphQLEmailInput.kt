package com.leftindust.mockingbird.graphql.types.input

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.graphql.types.GraphQLEmailType

@GraphQLName("EmailInput")
data class GraphQLEmailInput(
    val type: GraphQLEmailType,
    val email: String,
)

