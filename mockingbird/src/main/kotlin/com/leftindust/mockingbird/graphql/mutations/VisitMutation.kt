package com.leftindust.mockingbird.graphql.mutations

import com.expediagroup.graphql.server.operations.Mutation
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.dao.VisitDao
import com.leftindust.mockingbird.graphql.types.GraphQLVisit
import com.leftindust.mockingbird.graphql.types.input.GraphQLVisitEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLVisitInput
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class VisitMutation(
    private val visitDao: VisitDao
) : Mutation {
    suspend fun addVisit(
        visit: GraphQLVisitInput,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): GraphQLVisit = withContext(Dispatchers.IO) {
        visitDao.addVisit(visit, dataFetchingEnvironment.authToken)
    }.let(::GraphQLVisit)

    suspend fun editVisit(
        visit: GraphQLVisitEditInput,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): GraphQLVisit = withContext(Dispatchers.IO) {
        visitDao.editVisit(visit, dataFetchingEnvironment.authToken)
    }.let(::GraphQLVisit)
}