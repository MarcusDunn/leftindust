package com.leftindust.mockingbird.group

import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput
import com.leftindust.mockingbird.user.GraphQLUserGroup
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class GroupQuery(private val groupDao: GroupDao) : Query {
    suspend fun groups(
        gids: List<GraphQLUserGroup.ID>? = null,
        range: GraphQLRangeInput? = null,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): List<GraphQLUserGroup> = when {
        gids != null -> withContext(Dispatchers.IO) {
            gids.map { groupDao.getGroupById(it, dataFetchingEnvironment.authToken) }
        }
        range != null -> withContext(Dispatchers.IO) {
            groupDao.getRange(range, dataFetchingEnvironment.authToken)
        }
        else -> throw IllegalArgumentException("invalid argument combination to groups")
    }.map(::GraphQLUserGroup)
}