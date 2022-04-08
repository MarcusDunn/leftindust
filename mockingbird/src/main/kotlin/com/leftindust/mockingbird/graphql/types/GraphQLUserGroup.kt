package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.dao.entity.MediqGroup
import java.util.*

@GraphQLName("Group")
data class GraphQLUserGroup(
    val gid: ID,
    val name: String
) {
    @GraphQLName("GroupId")
    data class ID(val id: UUID)

    constructor(group: MediqGroup) : this(
        gid = ID(group.id!!),
        name = group.name
    )
}