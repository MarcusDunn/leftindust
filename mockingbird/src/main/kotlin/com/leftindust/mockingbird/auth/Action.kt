package com.leftindust.mockingbird.auth

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import java.sql.Timestamp
import java.util.*
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class Action(
    @Enumerated(EnumType.STRING)
    val referencedTableName: Tables,
    @Enumerated(EnumType.STRING)
    val permissionType: Crud,
    val startTime: Timestamp? = null,
    val endTime: Timestamp? = null,
    val rowId: UUID? = null,
    val columnName: String? = null,
) : AbstractJpaPersistable() {

    constructor(pair: Pair<Crud, Tables>) : this(
        permissionType = pair.first,
        referencedTableName = pair.second,
    )

    constructor(graphQLPermission: GraphQLPermissionInput) : this(
        referencedTableName = graphQLPermission.referencedTableName,
        permissionType = graphQLPermission.permissionType,
        startTime = graphQLPermission.startTime?.toTimestamp(),
        endTime = graphQLPermission.endTime?.toTimestamp(),
        rowId = graphQLPermission.rowId,
        columnName = graphQLPermission.columnName,
    )

    infix fun isSuperset(action: Action): Boolean =
        referencedTableName eqOrLhsIsNull action.referencedTableName &&
                permissionType eqOrLhsIsNull action.permissionType &&
                startTime gtOrLhsIsNull action.startTime &&
                endTime ltOrLhsIsNull action.endTime &&
                rowId eqOrLhsIsNull action.rowId &&
                columnName eqOrLhsIsNull action.columnName

    private infix fun <T> T.eqOrLhsIsNull(rhs: T) = this == rhs || this == null

    private infix fun Timestamp?.ltOrLhsIsNull(rhs: Timestamp?): Boolean {
        return this?.time == rhs?.time || this == null || this.time < (rhs?.time ?: return false)
    }

    private infix fun Timestamp?.gtOrLhsIsNull(rhs: Timestamp?): Boolean {
        return this?.time == rhs?.time || this == null || this.time < (rhs?.time ?: return false)
    }

    override fun toString(): String =
        "Action(referencedTableName=$referencedTableName, permissionType=$permissionType, startTime=$startTime, endTime=$endTime, rowId=$rowId, columnName=$columnName)"
}
