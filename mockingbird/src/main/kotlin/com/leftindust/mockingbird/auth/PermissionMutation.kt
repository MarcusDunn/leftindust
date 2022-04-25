package com.leftindust.mockingbird.auth

import com.expediagroup.graphql.server.operations.Mutation
import com.leftindust.mockingbird.persistance.PermissionDao
import com.leftindust.mockingbird.user.GraphQLUserGroup
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class PermissionMutation(private val permissionDao: PermissionDao) : Mutation {
    suspend fun addPermission(
        groupId: GraphQLUserGroup.ID? = null,
        userUid: String? = null,
        permission: GraphQLPermissionInput,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): GraphQLPermission = when {
        groupId == null && userUid != null -> permissionDao.addUserPermission(
            userUid,
            permission,
            dataFetchingEnvironment.authToken
        ).action.let(::GraphQLPermission)
        groupId != null && userUid == null -> permissionDao.addGroupPermission(
            groupId,
            permission,
            dataFetchingEnvironment.authToken
        ).action.let(::GraphQLPermission)
        else -> throw IllegalArgumentException("ONE of group or user must be specified when adding permissions")
    }
}