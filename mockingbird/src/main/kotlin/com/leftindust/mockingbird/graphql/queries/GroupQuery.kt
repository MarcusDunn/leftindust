package com.leftindust.mockingbird.graphql.queries

import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.dao.GroupDao
import com.leftindust.mockingbird.graphql.types.GraphQLUserGroup
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class GroupQuery(private val groupDao: GroupDao) : Query {
    // TODO: 2022-03-02 test this
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