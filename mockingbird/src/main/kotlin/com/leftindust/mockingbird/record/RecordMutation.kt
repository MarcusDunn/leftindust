package com.leftindust.mockingbird.record

import com.expediagroup.graphql.server.operations.Mutation
import com.leftindust.mockingbird.auth.authToken
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class RecordMutation(private val recordDao: RecordDao) : Mutation {
    suspend fun addRecord(
        record: GraphQLPatientRecordInput,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): GraphQLPatientRecord = withContext(Dispatchers.IO) {
        recordDao.addRecord(record, dataFetchingEnvironment.authToken)
    }.let(::GraphQLPatientRecord)
}