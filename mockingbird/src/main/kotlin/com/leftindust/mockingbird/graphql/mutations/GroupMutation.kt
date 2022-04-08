package com.leftindust.mockingbird.graphql.mutations

import com.expediagroup.graphql.server.operations.Mutation
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.GroupDao
import com.leftindust.mockingbird.graphql.types.GraphQLUserGroup
import com.leftindust.mockingbird.graphql.types.input.GraphQLGroupInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class GroupMutation(private val groupDao: GroupDao) : Mutation {
    // TODO: 2022-03-02 test this
    suspend fun addGroup(
        group: GraphQLGroupInput,
        graphQLAuthContext: GraphQLAuthContext
    ): GraphQLUserGroup = withContext(Dispatchers.IO) {
        groupDao.addGroup(group, graphQLAuthContext.mediqAuthToken)
    }.let { GraphQLUserGroup(it) }
}