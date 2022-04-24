package com.leftindust.mockingbird.record

import com.expediagroup.graphql.generator.exceptions.GraphQLKotlinException
import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.patient.GraphQLPatient
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class RecordQuery(
    private val recordDao: RecordDao,
) : Query {

    @Deprecated("removed due to lack of query verification", replaceWith = ReplaceWith("recordByPatient"))
    suspend fun records(
        pid: GraphQLPatient.ID? = null,
        rids: List<GraphQLPatientRecord.ID>? = null,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): List<GraphQLPatientRecord> = when {
        pid == null && rids != null -> rids.map {
            recordById(it, dataFetchingEnvironment)
        }
        pid != null && rids == null -> withContext(Dispatchers.IO) {
            recordByPatient(pid, dataFetchingEnvironment)
        }
        else -> throw GraphQLKotlinException("invalid argument combination to getRecords")
    }

    private fun recordByPatient(
        pid: GraphQLPatient.ID,
        dataFetchingEnvironment: DataFetchingEnvironment
    ) = recordDao
        .getRecordsByPatientPid(pid, dataFetchingEnvironment.authToken)
        .map(::GraphQLPatientRecord)

    private fun recordById(
        it: GraphQLPatientRecord.ID,
        dataFetchingEnvironment: DataFetchingEnvironment
    ) = recordDao
        .getRecordByRecordId(it, dataFetchingEnvironment.authToken)
        .let(::GraphQLPatientRecord)
}
