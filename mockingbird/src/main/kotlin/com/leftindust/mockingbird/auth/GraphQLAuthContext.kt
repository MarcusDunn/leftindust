package com.leftindust.mockingbird.auth

import com.expediagroup.graphql.server.spring.execution.SpringGraphQLContext
import org.springframework.web.reactive.function.server.ServerRequest

/**
 * the mediq specific data returned from the ContextFactory
 * @property mediqAuthToken the authentication token
 */
data class GraphQLAuthContext(val mediqAuthToken: MediqToken, val request: ServerRequest) : SpringGraphQLContext(request)