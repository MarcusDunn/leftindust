package com.leftindust.mockingbird.user

import com.expediagroup.graphql.server.operations.Mutation
import com.leftindust.mockingbird.auth.authToken
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class UserMutation(
    private val userDao: UserDao,
) : Mutation {
    suspend fun addUser(
        user: GraphQLUserInput,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): GraphQLUser = withContext(Dispatchers.IO) {
        userDao
            .addUser(user, dataFetchingEnvironment.authToken)
    }.let(::GraphQLUser)

    suspend fun editUser(
        user: GraphQLUserEditInput,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): GraphQLUser = withContext(Dispatchers.IO) {
        userDao.updateUser(user, dataFetchingEnvironment.authToken)
    }.let(::GraphQLUser)
}