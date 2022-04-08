package com.leftindust.mockingbird.graphql.types.input

import com.expediagroup.graphql.generator.annotations.GraphQLName

@GraphQLName("GroupInput")
data class GraphQLGroupInput(
    val name: String
)
