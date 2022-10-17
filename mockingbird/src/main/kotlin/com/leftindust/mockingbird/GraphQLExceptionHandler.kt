package com.leftindust.mockingbird

import graphql.ErrorType
import graphql.GraphQLError
import graphql.GraphqlErrorBuilder
import graphql.schema.DataFetchingEnvironment
import io.ktor.util.reflect.*
import mu.KotlinLogging
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter
import org.springframework.stereotype.Component

@Component
class GraphQLExceptionHandler : DataFetcherExceptionResolverAdapter() {

    private val logger = KotlinLogging.logger { }

    override fun resolveToSingleError(e: Throwable, env: DataFetchingEnvironment): GraphQLError? {
        return when (e) {
            is MockingbirdException -> e.toGraphQLError()
            else -> super.resolveToSingleError(e, env).also { logger.warn("Caught an unhandled exception: ${e.message}", e) }
        }
    }
}