package com.leftindust.mockingbird.group

import com.expediagroup.graphql.generator.annotations.GraphQLName

@GraphQLName("GroupInput")
data class GraphQLGroupInput(
    val name: String
)
