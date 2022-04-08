package com.leftindust.mockingbird.graphql.mutations

import com.expediagroup.graphql.server.operations.Mutation
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.PermissionDao
import com.leftindust.mockingbird.graphql.types.GraphQLPermission
import com.leftindust.mockingbird.graphql.types.GraphQLUserGroup
import com.leftindust.mockingbird.graphql.types.input.GraphQLPermissionInput
import org.springframework.stereotype.Component

@Component
class PermissionMutation(private val permissionDao: PermissionDao) : Mutation {
    suspend fun addPermission(
        groupId: GraphQLUserGroup.ID? = null,
        userUid: String? = null,
        permission: GraphQLPermissionInput,
        graphQLAuthContext: GraphQLAuthContext
    ): GraphQLPermission {
        return when {
            groupId == null && userUid != null -> {
                permissionDao.addUserPermission(
                    userUid,
                    permission,
                    graphQLAuthContext.mediqAuthToken
                ).action.let { GraphQLPermission(it) }
            }
            groupId != null && userUid == null -> {
                permissionDao.addGroupPermission(
                    groupId,
                    permission,
                    graphQLAuthContext.mediqAuthToken
                ).action.let { GraphQLPermission(it) }
            }
            else -> throw IllegalArgumentException("ONE of group or user must be specified when adding permissions")
        }
    }
}