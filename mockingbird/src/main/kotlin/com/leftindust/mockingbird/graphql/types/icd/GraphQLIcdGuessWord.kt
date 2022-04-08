package com.leftindust.mockingbird.graphql.types.icd

import com.expediagroup.graphql.generator.annotations.GraphQLName

@GraphQLName("IcdGuessWord")
data class GraphQLIcdGuessWord(
    val label: String?,
    val dontChangeResult: String,
)
