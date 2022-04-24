package com.leftindust.mockingbird.group

import com.expediagroup.graphql.server.operations.Mutation
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.user.GraphQLUserGroup
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class GroupMutation(private val groupDao: GroupDao) : Mutation {
    suspend fun addGroup(
        group: GraphQLGroupInput,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): GraphQLUserGroup = withContext(Dispatchers.IO) {
        groupDao.addGroup(group, dataFetchingEnvironment.authToken)
    }.let(::GraphQLUserGroup)
}