package com.leftindust.mockingbird.graphql.mutations

import com.expediagroup.graphql.server.operations.Mutation
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.dao.RecordDao
import com.leftindust.mockingbird.graphql.types.GraphQLPatientRecord
import com.leftindust.mockingbird.graphql.types.input.GraphQLPatientRecordInput
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