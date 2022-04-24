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
    @Deprecated("removed due to lack of query verification", replaceWith = ReplaceWith("groupsByRange"))
    suspend fun groups(
        gids: List<GraphQLUserGroup.ID>? = null,
        range: GraphQLRangeInput? = null,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): List<GraphQLUserGroup> = when {
        gids != null -> withContext(Dispatchers.IO) {
            groupsById(gids, dataFetchingEnvironment)
        }
        range != null -> withContext(Dispatchers.IO) {
            groupsByRange(range, dataFetchingEnvironment)
        }
        else -> throw IllegalArgumentException("invalid argument combination to groups")
    }

    fun groupsByRange(
        range: GraphQLRangeInput,
        dataFetchingEnvironment: DataFetchingEnvironment
    ) = groupDao.getRange(range, dataFetchingEnvironment.authToken).map(::GraphQLUserGroup)

    fun groupsById(
        gids: List<GraphQLUserGroup.ID>,
        dataFetchingEnvironment: DataFetchingEnvironment
    ) = gids.map { groupDao.getGroupById(it, dataFetchingEnvironment.authToken) }.map(::GraphQLUserGroup)
}