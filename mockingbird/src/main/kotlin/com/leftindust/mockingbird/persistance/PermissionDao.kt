package com.leftindust.mockingbird.persistance

import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.auth.AccessControlList
import com.leftindust.mockingbird.user.GraphQLUserGroup
import com.leftindust.mockingbird.auth.GraphQLPermissionInput

interface PermissionDao {
    fun addUserPermission(
        uid: String,
        permission: GraphQLPermissionInput,
        requester: MediqToken
    ): AccessControlList

    fun addGroupPermission(
        gid: GraphQLUserGroup.ID,
        permission: GraphQLPermissionInput,
        requester: MediqToken
    ): AccessControlList
}