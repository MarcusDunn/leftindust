package com.leftindust.mockingbird.patient

import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput
import com.leftindust.mockingbird.graphql.types.search.example.GraphQLPatientExample
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class PatientQuery(
    private val patientDao: ReadPatientDao,
) : Query {
    suspend fun patientsByRange(
        range: GraphQLRangeInput,
        sortedBy: Patient.SortableField? = null,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): List<GraphQLPatient> = withContext(Dispatchers.IO) {
        patientDao.getMany(range, sortedBy ?: Patient.SortableField.PID, dataFetchingEnvironment.authToken)
    }.map(::GraphQLPatient)

    suspend fun patientsByPid(
        pids: List<GraphQLPatient.ID>,
        sortedBy: Patient.SortableField? = null,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): List<GraphQLPatient> = withContext(Dispatchers.IO) {
        patientDao.getPatientsByPids(pids, dataFetchingEnvironment.authToken)
    }
        .sortedBy { (sortedBy ?: Patient.SortableField.PID).instanceValue(it) }
        .map(::GraphQLPatient)

    suspend fun patientsByExample(
        example: GraphQLPatientExample,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): List<GraphQLPatient> = withContext(Dispatchers.IO) {
        patientDao.searchByExample(example, dataFetchingEnvironment.authToken)
    }
        .distinctBy { it.id }
        .map(::GraphQLPatient)
}
