package com.leftindust.mockingbird.event

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import com.leftindust.mockingbird.auth.authToken
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class EventMutation(private val eventDao: EventDao) : Mutation {
    suspend fun addEvent(
        event: GraphQLEventInput,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): GraphQLEvent = withContext(Dispatchers.IO) {
        eventDao.addEvent(event, dataFetchingEnvironment.authToken)
    }.let(::GraphQLEvent)

    @GraphQLDescription("edits the event referenced by eid")
    suspend fun editEvent(
        event: GraphQLEventEditInput,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): GraphQLEvent = withContext(Dispatchers.IO) {
        eventDao.editEvent(event, dataFetchingEnvironment.authToken)
    }.let(::GraphQLEvent)

    @GraphQLDescription("edits the event referenced by eid")
    suspend fun editRecurringEvent(
        event: GraphQLEventEditInput,
        recurrenceSettings: GraphQLRecurrenceEditSettings,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): GraphQLEvent = withContext(Dispatchers.IO) {
        eventDao.editRecurringEvent(event, dataFetchingEnvironment.authToken, recurrenceSettings)
    }.let(::GraphQLEvent)
}
