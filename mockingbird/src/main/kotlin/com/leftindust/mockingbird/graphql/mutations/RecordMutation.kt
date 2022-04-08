package com.leftindust.mockingbird.graphql.mutations

import com.expediagroup.graphql.server.operations.Mutation
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.RecordDao
import com.leftindust.mockingbird.graphql.types.GraphQLRecord
import com.leftindust.mockingbird.graphql.types.input.GraphQLRecordInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class RecordMutation(private val recordDao: RecordDao) : Mutation {
    suspend fun addRecord(
        record: GraphQLRecordInput,
        graphQLAuthContext: GraphQLAuthContext
    ): GraphQLRecord = withContext(Dispatchers.IO) {
        recordDao.addRecord(record, graphQLAuthContext.mediqAuthToken)
    }.let { GraphQLRecord(it, graphQLAuthContext) }
}