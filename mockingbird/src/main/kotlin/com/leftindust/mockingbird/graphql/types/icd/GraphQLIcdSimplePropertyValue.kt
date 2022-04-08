package com.leftindust.mockingbird.graphql.types.icd

import com.expediagroup.graphql.generator.annotations.GraphQLName

@GraphQLName("IcdSimplePropertyValue")
data class GraphQLIcdSimplePropertyValue(
    val propertyId: String?,
    val label: String?,
    val score: Double?,
    val important: Boolean?
)