package com.leftindust.mockingbird.graphql.mutations

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.dao.EventDao
import com.leftindust.mockingbird.graphql.types.GraphQLEvent
import com.leftindust.mockingbird.graphql.types.input.GraphQLEventEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLEventInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLRecurrenceEditSettings
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
