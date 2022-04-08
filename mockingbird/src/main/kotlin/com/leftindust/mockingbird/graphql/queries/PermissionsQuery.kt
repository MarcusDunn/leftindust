package com.leftindust.mockingbird.graphql.queries

import com.expediagroup.graphql.generator.exceptions.GraphQLKotlinException
import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.AuthorizationDao
import com.leftindust.mockingbird.graphql.types.GraphQLPermissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class PermissionsQuery(
    private val authorizer: AuthorizationDao
) : Query {
    suspend fun permissions(
        uid: String,
        graphQLAuthContext: GraphQLAuthContext
    ): GraphQLPermissions = if (graphQLAuthContext.mediqAuthToken.isVerified()) {
        withContext(Dispatchers.IO) {
            authorizer.getRolesForUserByUid(uid)
        }.let { GraphQLPermissions(it) }
    } else {
        throw GraphQLKotlinException("unauthenticated token")
    }
}