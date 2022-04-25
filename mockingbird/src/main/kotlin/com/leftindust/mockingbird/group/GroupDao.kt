package com.leftindust.mockingbird.group

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput
import com.leftindust.mockingbird.user.GraphQLUserGroup

interface GroupDao {
    @Blocking
    fun addGroup(group: GraphQLGroupInput, requester: MediqToken): MediqGroup

    @Blocking
    fun getGroupById(gid: GraphQLUserGroup.ID, requester: MediqToken): MediqGroup

    @Blocking
    fun getRange(range: GraphQLRangeInput, requester: MediqToken): Collection<MediqGroup>
}
