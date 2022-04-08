package com.leftindust.mockingbird.graphql.mutations

import com.expediagroup.graphql.server.operations.Mutation
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.UserDao
import com.leftindust.mockingbird.graphql.types.GraphQLUser
import com.leftindust.mockingbird.graphql.types.input.GraphQLUserEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLUserInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class UserMutation(
    private val userDao: UserDao,
) : Mutation {
    suspend fun addUser(
        user: GraphQLUserInput,
        graphQLAuthContext: GraphQLAuthContext
    ): GraphQLUser = withContext(Dispatchers.IO) {
        userDao
            .addUser(user, graphQLAuthContext.mediqAuthToken)
    }.let { GraphQLUser(it, graphQLAuthContext) }

    suspend fun editUser(
        user: GraphQLUserEditInput,
        graphQLAuthContext: GraphQLAuthContext
    ): GraphQLUser = withContext(Dispatchers.IO) {
        userDao.updateUser(user, graphQLAuthContext.mediqAuthToken)
    }.let { GraphQLUser(it, graphQLAuthContext) }
}