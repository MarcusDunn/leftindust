package com.leftindust.mockingbird.auth

import com.leftindust.mockingbird.graphql.types.GraphQLUtcTime
import java.util.*

data class GraphQLPermissionInput(
    val referencedTableName: Tables,
    val permissionType: Crud,
    val startTime: GraphQLUtcTime? = null,
    val endTime: GraphQLUtcTime? = null,
    val rowId: UUID? = null,
    val columnName: String? = null,
)

