package com.leftindust.mockingbird.auth


import com.expediagroup.graphql.server.spring.execution.DefaultSpringGraphQLContextFactory
import graphql.schema.DataFetchingEnvironment
import org.apache.logging.log4j.LogManager
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest

val MediqToken.Companion.CONTEXT_MAP_KEY: Any
    get() = MediqToken::class

val DataFetchingEnvironment.authToken: MediqToken
    get() = graphQlContext[MediqToken.CONTEXT_MAP_KEY]

@Component
class ContextFactory : DefaultSpringGraphQLContextFactory() {
    private val logger = LogManager.getLogger()

    companion object {
        private val bearerTokenRegex = Regex("""Bearer (.*)""")
    }

    override suspend fun generateContextMap(request: ServerRequest): Map<*, Any> {
        val contextMap = super.generateContextMap(request)
        val authContextMap = if (request.method() == HttpMethod.OPTIONS) {
            return contextMap
        } else {
            val authorization = request.headers().firstHeader("Authorization")
                ?: return contextMap
            val (token) = bearerTokenRegex.matchEntire(authorization)?.destructured
                ?: return contextMap
            mapOf(MediqToken.CONTEXT_MAP_KEY to FirebaseToken(token))
        }.also { logger.info("request $request has context map $it") }
        return contextMap + authContextMap
    }
}



