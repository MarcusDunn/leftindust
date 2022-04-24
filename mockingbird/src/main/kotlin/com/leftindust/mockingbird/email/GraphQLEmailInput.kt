package com.leftindust.mockingbird.email

import com.expediagroup.graphql.generator.annotations.GraphQLName

@GraphQLName("EmailInput")
data class GraphQLEmailInput(
    val type: GraphQLEmailType,
    val email: String,
)

