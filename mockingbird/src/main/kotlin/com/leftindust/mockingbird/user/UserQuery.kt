package com.leftindust.mockingbird.user

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.exceptions.GraphQLKotlinException
import com.expediagroup.graphql.generator.scalars.ID
import com.expediagroup.graphql.server.operations.Query
import com.google.firebase.auth.ExportedUserRecord
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.NotAuthorizedException
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.auth.Tables
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class UserQuery(
    private val userDao: UserDao,
    private val firebaseFetcher: UserFetcher
) : Query {
    @GraphQLDescription("attempts to find the mediq-registered user by uid, if the user does not exist in the DB")
    suspend fun user(uid: ID, dataFetchingEnvironment: DataFetchingEnvironment): GraphQLUser {
        val strUid = uid.value
        val authToken = dataFetchingEnvironment.authToken
        if (strUid == "admin") throw GraphQLKotlinException("you trying to break something?")
        val user = withContext(Dispatchers.IO) { userDao.findUserByUid(strUid, authToken) }
        return if (user == null) {
            if (authToken.isVerified()) {
                val fireBaseUser = firebaseFetcher.getUserInfo(strUid, authToken)
                GraphQLUser(
                    uid = fireBaseUser.uid,
                    group = null,
                )
            } else {
                throw NotAuthorizedException(authToken, Crud.READ to Tables.User)
            }
        } else {
            GraphQLUser(user)
        }
    }

    @GraphQLDescription("returns a list of users, only one argument should be specified")
    suspend fun users(
        range: GraphQLRangeInput? = null,
        uniqueIds: List<ID>? = null,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): List<GraphQLUser> = when {
        uniqueIds != null -> uniqueIds.map { userDao.findUserByUid(it.value, dataFetchingEnvironment.authToken)!! }
        range != null -> withContext(Dispatchers.IO) { userDao.getUsers(range, dataFetchingEnvironment.authToken) }
        else -> throw IllegalArgumentException("invalid argument combination to users")
    }
        .filter { it.uniqueId != "admin" }
        .map(::GraphQLUser)

    @GraphQLDescription(
        """gets all users from firebase, you can
filter out already registered 
users by setting filterRegistered 
to true (defaults to false)"""
    )
    suspend fun firebaseUsers(
        range: GraphQLRangeInput? = null,
        filterRegistered: Boolean? = false,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): List<GraphQLFirebaseInfo> {
        val users = firebaseFetcher.getUsers(dataFetchingEnvironment.authToken)
        val nnRange = range ?: GraphQLRangeInput(0, 20)
        val validatedRange = nnRange.toIntRange()

        val returnedUsers = emptyList<ExportedUserRecord>().toMutableList()
        users.takeWhile { returnedUsers.size < validatedRange.last }
            .filter { (userDao.findUserByUid(it.uid, dataFetchingEnvironment.authToken) != null) == filterRegistered }
            .forEach { returnedUsers.add(it) }

        return returnedUsers
            .drop(validatedRange.first)
            .map { GraphQLFirebaseInfo(it) }
    }
}