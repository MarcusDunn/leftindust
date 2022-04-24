package com.leftindust.mockingbird.auth

import com.expediagroup.graphql.generator.exceptions.GraphQLKotlinException
import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class PermissionsQuery(
    private val authorizer: AuthorizationDao
) : Query {
    suspend fun permissions(
        uid: String,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): GraphQLPermissions = if (dataFetchingEnvironment.authToken.isVerified()) {
        withContext(Dispatchers.IO) {
            authorizer.getRolesForUserByUid(uid)
        }.let { GraphQLPermissions(it) }
    } else {
        throw GraphQLKotlinException("unauthenticated token")
    }
}