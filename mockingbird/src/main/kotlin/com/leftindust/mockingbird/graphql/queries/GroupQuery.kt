package com.leftindust.mockingbird.graphql.queries

import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.GroupDao
import com.leftindust.mockingbird.graphql.types.GraphQLUserGroup
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class GroupQuery(private val groupDao: GroupDao) : Query {
    // TODO: 2022-03-02 test this
    suspend fun groups(
        gids: List<GraphQLUserGroup.ID>? = null,
        range: GraphQLRangeInput? = null,
        graphQLAuthContext: GraphQLAuthContext
    ): List<GraphQLUserGroup> {
        return when {
            gids != null -> gids.map { groupDao.getGroupById(it, graphQLAuthContext.mediqAuthToken) }
                .map { GraphQLUserGroup(it) }
            range != null -> withContext(Dispatchers.IO) {
                groupDao.getRange(range, graphQLAuthContext.mediqAuthToken)
            }.map { GraphQLUserGroup(it) }
            else -> throw IllegalArgumentException("invalid argument combination to groups")
        }
    }
}