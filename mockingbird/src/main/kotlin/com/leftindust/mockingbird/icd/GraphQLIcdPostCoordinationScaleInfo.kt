package com.leftindust.mockingbird.icd

data class GraphQLIcdPostCoordinationScaleInfo(
    val id: String?,
    val axisName: String?,
    val requiredPostcoordination: Boolean,
    val allowMultipleValues: String,
    val scaleEntity: List<String>,
)