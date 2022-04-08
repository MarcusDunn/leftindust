package com.leftindust.mockingbird.auth


import com.expediagroup.graphql.server.spring.execution.SpringGraphQLContextFactory
import com.leftindust.mockingbird.auth.impl.VerifiedFirebaseToken
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest

/**
 * Handles turning headers into GraphQLContext
 */
@Component
class ContextFactory : SpringGraphQLContextFactory<GraphQLAuthContext>() {
    override suspend fun generateContext(request: ServerRequest): GraphQLAuthContext {
        return if (request.method() == HttpMethod.OPTIONS) {
            GraphQLAuthContext(VerifiedFirebaseToken(null), request)
        } else {
            val token = request.headers().firstHeader("mediq-auth-token")
                ?: return GraphQLAuthContext(VerifiedFirebaseToken(null), request)
            GraphQLAuthContext(VerifiedFirebaseToken(token), request)
        }
    }
}

