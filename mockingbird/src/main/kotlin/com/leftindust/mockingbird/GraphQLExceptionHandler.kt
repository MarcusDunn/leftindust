package com.leftindust.mockingbird

import graphql.GraphQLError
import graphql.schema.DataFetchingEnvironment
import mu.KotlinLogging
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger { }
@Component
class GraphQLExceptionHandler : DataFetcherExceptionResolverAdapter() {
    override fun resolveToSingleError(e: Throwable, env: DataFetchingEnvironment): GraphQLError? {
        return when (e) {
            is MockingbirdException -> e.toGraphQLError()
            else -> super.resolveToSingleError(e, env).also { logger.error(e)  { "Caught an unhandled exception" } }
        }
    }
}