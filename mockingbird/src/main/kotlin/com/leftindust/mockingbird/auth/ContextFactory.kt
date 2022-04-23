package com.leftindust.mockingbird.auth


import com.expediagroup.graphql.server.spring.execution.DefaultSpringGraphQLContextFactory
import com.leftindust.mockingbird.auth.impl.VerifiedFirebaseToken
import graphql.schema.DataFetchingEnvironment
import org.apache.logging.log4j.LogManager
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest

val MediqToken.Companion.CONTEXT_MAP_KEY: Any
    get() = MediqToken::class

val DataFetchingEnvironment.authToken: MediqToken
    get() = graphQlContext[MediqToken.CONTEXT_MAP_KEY]

/**
 * Handles turning headers into GraphQLContext
 */
@Component
class ContextFactory : DefaultSpringGraphQLContextFactory() {
    private val logger = LogManager.getLogger()

    override suspend fun generateContextMap(request: ServerRequest): Map<*, Any> =
        super.generateContextMap(request) + if (request.method() == HttpMethod.OPTIONS) {
            emptyMap()
        } else {
            mapOf(
                MediqToken.CONTEXT_MAP_KEY to VerifiedFirebaseToken(
                    request.headers().firstHeader("mediq-auth-token")
                )
            )
        }.also { logger.info("request $request has context map $it") }
}



