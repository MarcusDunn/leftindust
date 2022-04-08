package com.leftindust.mockingbird.graphql.queries

import com.expediagroup.graphql.generator.exceptions.GraphQLKotlinException
import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.RecordDao
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.GraphQLRecord
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
        authContext: GraphQLAuthContext
    ): List<GraphQLRecord> {
        val requester = authContext.mediqAuthToken
        return when {
            pid == null && rids != null -> {
                rids.map { recordDao.getRecordByRecordId(it, requester) }.map { GraphQLRecord(it, authContext) }
            }
            pid != null && rids == null -> {
                withContext(Dispatchers.IO) {
                    recordDao.getRecordsByPatientPid(pid, requester)
                }.map { GraphQLRecord(it, authContext) }
            }
            else -> throw GraphQLKotlinException("invalid argument combination to getRecords")
        }
    }
}
