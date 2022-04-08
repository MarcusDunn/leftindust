package com.leftindust.mockingbird.graphql.queries

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.exceptions.GraphQLKotlinException
import com.expediagroup.graphql.generator.scalars.ID
import com.expediagroup.graphql.server.operations.Query
import com.google.firebase.auth.ExportedUserRecord
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.auth.NotAuthorizedException
import com.leftindust.mockingbird.dao.Tables
import com.leftindust.mockingbird.dao.UserDao
import com.leftindust.mockingbird.external.firebase.UserFetcher
import com.leftindust.mockingbird.graphql.types.GraphQLFirebaseInfo
import com.leftindust.mockingbird.graphql.types.GraphQLUser
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class UserQuery(
    private val userDao: UserDao,
    private val firebaseFetcher: UserFetcher
) : Query {
    @GraphQLDescription("attempts to find the mediq-registered user by uid, if the user does not exist in the DB")
    suspend fun user(uid: ID, graphQLAuthContext: GraphQLAuthContext): GraphQLUser {
        val strUid = uid.value
        if (strUid == "admin") throw GraphQLKotlinException("you trying to break something?")
        val user = withContext(Dispatchers.IO) { userDao.findUserByUid(strUid, graphQLAuthContext.mediqAuthToken) }
        return if (user == null) {
            if (graphQLAuthContext.mediqAuthToken.isVerified()) {
                val fireBaseUser = firebaseFetcher.getUserInfo(strUid, graphQLAuthContext.mediqAuthToken)
                GraphQLUser(
                    uid = fireBaseUser.uid,
                    group = null,
                    authContext = graphQLAuthContext
                )
            } else {
                throw NotAuthorizedException(graphQLAuthContext.mediqAuthToken, Crud.READ to Tables.User)
            }
        } else {
            GraphQLUser(user, graphQLAuthContext)
        }
    }

    @GraphQLDescription("returns a list of users, only one argument should be specified")
    suspend fun users(
        range: GraphQLRangeInput? = null,
        uniqueIds: List<ID>? = null,
        graphQLAuthContext: GraphQLAuthContext
    ): List<GraphQLUser> {
        return when {
            uniqueIds != null -> uniqueIds.map { userDao.findUserByUid(it.value, graphQLAuthContext.mediqAuthToken)!! }
            range != null -> withContext(Dispatchers.IO) { userDao.getUsers(range, graphQLAuthContext.mediqAuthToken) }
            else -> throw IllegalArgumentException("invalid argument combination to users")
        }.filter { it.uniqueId != "admin" }.map { GraphQLUser(it, graphQLAuthContext) }
    }

    @GraphQLDescription(
        """gets all users from firebase, you can
filter out already registered 
users by setting filterRegistered 
to true (defaults to false)"""
    )
    suspend fun firebaseUsers(
        range: GraphQLRangeInput? = null,
        filterRegistered: Boolean? = false,
        graphQLAuthContext: GraphQLAuthContext
    ): List<GraphQLFirebaseInfo> {
        val users = firebaseFetcher.getUsers(graphQLAuthContext.mediqAuthToken)
        val nnRange = range ?: GraphQLRangeInput(0, 20)
        val validatedRange = nnRange.toIntRange()

        val returnedUsers = emptyList<ExportedUserRecord>().toMutableList()
        users.takeWhile { returnedUsers.size < validatedRange.last }
            .filter { (userDao.findUserByUid(it.uid, graphQLAuthContext.mediqAuthToken) != null) == filterRegistered }
            .forEach { returnedUsers.add(it) }

        return returnedUsers
            .drop(validatedRange.first)
            .map { GraphQLFirebaseInfo(it) }
    }
}