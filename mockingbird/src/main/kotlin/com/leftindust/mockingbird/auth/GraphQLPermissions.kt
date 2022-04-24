package com.leftindust.mockingbird.auth

import com.expediagroup.graphql.generator.annotations.GraphQLName

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

