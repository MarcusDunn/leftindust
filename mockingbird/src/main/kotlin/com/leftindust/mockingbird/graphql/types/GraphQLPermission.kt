package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.dao.Tables
import com.leftindust.mockingbird.dao.entity.Action
import java.util.*

@GraphQLName("Permission")
data class GraphQLPermission(
    val pid: ID,
    val referencedTableName: Tables,
    val permissionType: Crud,
    val startTime: GraphQLUtcTime? = null,
    val endTime: GraphQLUtcTime? = null,
    val rowId: UUID? = null,
    val columnName: String? = null,
) {

    @GraphQLName("PermissionId")
    data class ID(val id: UUID)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GraphQLPermission) return false

        if (referencedTableName != other.referencedTableName) return false
        if (permissionType != other.permissionType) return false
        if (startTime != other.startTime) return false
        if (endTime != other.endTime) return false
        if (rowId != other.rowId) return false
        if (columnName != other.columnName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = referencedTableName.hashCode()
        result = 31 * result + permissionType.hashCode()
        result = 31 * result + (startTime?.hashCode() ?: 0)
        result = 31 * result + (endTime?.hashCode() ?: 0)
        result = 31 * result + (rowId?.hashCode() ?: 0)
        result = 31 * result + (columnName?.hashCode() ?: 0)
        return result
    }

    constructor(action: Action) : this(
        pid = ID(action.id!!),
        referencedTableName = action.referencedTableName,
        permissionType = action.permissionType,
        startTime = action.startTime?.let { GraphQLUtcTime(it) },
        endTime = action.endTime?.let { GraphQLUtcTime(it) },
        rowId = action.rowId,
        columnName = action.columnName,
    )

}