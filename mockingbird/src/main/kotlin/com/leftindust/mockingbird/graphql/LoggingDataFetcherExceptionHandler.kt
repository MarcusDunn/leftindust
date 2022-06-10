package com.leftindust.mockingbird.graphql

import graphql.ExceptionWhileDataFetching
import graphql.execution.DataFetcherExceptionHandler
import graphql.execution.DataFetcherExceptionHandlerParameters
import graphql.execution.DataFetcherExceptionHandlerResult
import java.util.concurrent.CompletableFuture
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class LoggingDataFetcherExceptionHandler : DataFetcherExceptionHandler {
    private val logger = KotlinLogging.logger {  }
    override fun handleException(handlerParameters: DataFetcherExceptionHandlerParameters): CompletableFuture<DataFetcherExceptionHandlerResult> {
        val sourceLocation = handlerParameters.sourceLocation
        val path = handlerParameters.path
        val error = ExceptionWhileDataFetching(path, handlerParameters.exception, sourceLocation)
        logger.error(handlerParameters.exception) { error }
        return CompletableFuture.completedFuture(DataFetcherExceptionHandlerResult.newResult().error(error).build())
    }
}