package com.leftindust.mockingbird.person

import com.expediagroup.graphql.generator.annotations.GraphQLName

@GraphQLName("NameInput")
data class GraphQLNameInfoInput(
    val firstName: String,
    val middleName: String? = null,
    val lastName: String,
)