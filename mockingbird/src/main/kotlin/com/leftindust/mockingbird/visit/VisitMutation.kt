package com.leftindust.mockingbird.visit

import com.expediagroup.graphql.server.operations.Mutation
import com.leftindust.mockingbird.auth.authToken
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