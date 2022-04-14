package com.leftindust.mockingbird.graphql.queries

import com.expediagroup.graphql.generator.exceptions.GraphQLKotlinException
import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.dao.RecordDao
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.GraphQLRecord
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class RecordQuery(
    private val recordDao: RecordDao,
) : Query {
    suspend fun records(
        pid: GraphQLPatient.ID? = null,
        rids: List<GraphQLRecord.ID>? = null,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): List<GraphQLRecord> = when {
        pid == null && rids != null -> rids.map {
            recordDao.getRecordByRecordId(it, dataFetchingEnvironment.authToken)
        }
        pid != null && rids == null -> withContext(Dispatchers.IO) {
            recordDao.getRecordsByPatientPid(pid, dataFetchingEnvironment.authToken)
        }
        else -> throw GraphQLKotlinException("invalid argument combination to getRecords")
    }.map(::GraphQLRecord)
}
