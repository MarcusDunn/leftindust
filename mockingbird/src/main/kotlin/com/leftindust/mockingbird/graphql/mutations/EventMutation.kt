package com.leftindust.mockingbird.graphql.mutations

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.EventDao
import com.leftindust.mockingbird.graphql.types.GraphQLEvent
import com.leftindust.mockingbird.graphql.types.input.GraphQLEventEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLEventInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLRecurrenceEditSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class EventMutation(private val eventDao: EventDao) : Mutation {
    suspend fun addEvent(
        event: GraphQLEventInput,
        graphQLAuthContext: GraphQLAuthContext
    ): GraphQLEvent = withContext(Dispatchers.IO) {
        eventDao.addEvent(event, graphQLAuthContext.mediqAuthToken)
    }.let {
        GraphQLEvent(
            event = it,
            authContext = graphQLAuthContext
        )
    }

    @GraphQLDescription("edits the event referenced by eid")
    suspend fun editEvent(
        event: GraphQLEventEditInput,
        graphQLAuthContext: GraphQLAuthContext,
    ): GraphQLEvent = withContext(Dispatchers.IO) {
        eventDao.editEvent(event, graphQLAuthContext.mediqAuthToken)
    }.let { GraphQLEvent(it, graphQLAuthContext) }

    @GraphQLDescription("edits the event referenced by eid")
    suspend fun editRecurringEvent(
        event: GraphQLEventEditInput,
        graphQLAuthContext: GraphQLAuthContext,
        recurrenceSettings: GraphQLRecurrenceEditSettings
    ): GraphQLEvent = withContext(Dispatchers.IO) {
        eventDao.editRecurringEvent(event, graphQLAuthContext.mediqAuthToken, recurrenceSettings)
    }.let { GraphQLEvent(it, graphQLAuthContext) }
}
