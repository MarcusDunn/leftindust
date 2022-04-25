package com.leftindust.mockingbird.icd

import com.expediagroup.graphql.generator.annotations.GraphQLName

@GraphQLName("IcdTerm")
data class GraphQLIcdTerm(
    val label: GraphQLIcdLanguageSpecificText,
    val foundationReference: String?,
    val linearizationReference: String?,
)
