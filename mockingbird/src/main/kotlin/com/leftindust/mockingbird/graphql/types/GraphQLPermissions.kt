package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.dao.entity.AccessControlList

@GraphQLName("Permissions")
data class GraphQLPermissions(
    val groupPerms: List<GraphQLPermission>,
    val userPerms: List<GraphQLPermission>,
) {
    constructor(perms: List<AccessControlList>) : this(perms.partition { it.group != null })

    private constructor(perms: Pair<List<AccessControlList>, List<AccessControlList>>) : this(
        groupPerms = perms.first.map { GraphQLPermission(it.action) },
        userPerms = perms.second.map { GraphQLPermission(it.action) },
    )
}

