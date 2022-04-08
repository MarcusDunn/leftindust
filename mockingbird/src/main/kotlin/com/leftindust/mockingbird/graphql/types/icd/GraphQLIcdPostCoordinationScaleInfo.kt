package com.leftindust.mockingbird.graphql.types.icd

import com.expediagroup.graphql.generator.annotations.GraphQLName

@GraphQLName("IcdPostCoordinationScaleInfo")
data class GraphQLIcdPostCoordinationScaleInfo(
    val id: String?,
    val axisName: String?,
    val requiredPostcoordination: Boolean,
    val allowMultipleValues: String,
    val scaleEntity: List<String>,
)