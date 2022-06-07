package com.leftindust.mockingbird.icd

data class GraphQLIcdTerm(
    val label: GraphQLIcdLanguageSpecificText,
    val foundationReference: String?,
    val linearizationReference: String?,
)
