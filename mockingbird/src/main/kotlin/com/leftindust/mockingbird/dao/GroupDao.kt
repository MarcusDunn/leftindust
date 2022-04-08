package com.leftindust.mockingbird.dao

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.dao.entity.MediqGroup
import com.leftindust.mockingbird.graphql.types.GraphQLUserGroup
import com.leftindust.mockingbird.graphql.types.input.GraphQLGroupInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput

interface GroupDao {
    @Blocking
    fun addGroup(group: GraphQLGroupInput, requester: MediqToken): MediqGroup

    @Blocking
    fun getGroupById(gid: GraphQLUserGroup.ID, requester: MediqToken): MediqGroup

    @Blocking
    fun getRange(range: GraphQLRangeInput, requester: MediqToken): Collection<MediqGroup>
}
