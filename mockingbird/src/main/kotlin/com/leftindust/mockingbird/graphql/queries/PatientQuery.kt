package com.leftindust.mockingbird.graphql.queries

import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.entity.Patient
import com.leftindust.mockingbird.dao.patient.ReadPatientDao
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput
import com.leftindust.mockingbird.graphql.types.search.example.GraphQLPatientExample
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
        authContext: GraphQLAuthContext
    ): List<GraphQLPatient> = withContext(Dispatchers.IO) {
        patientDao.getMany(range, sortedBy ?: Patient.SortableField.PID, authContext.mediqAuthToken)
    }.map { GraphQLPatient(it, authContext) }

    suspend fun patientsByPid(
        pids: List<GraphQLPatient.ID>,
        sortedBy: Patient.SortableField? = null,
        authContext: GraphQLAuthContext,
    ): List<GraphQLPatient> = withContext(Dispatchers.IO) {
        patientDao.getPatientsByPids(pids, authContext.mediqAuthToken)
    }
        .sortedBy { (sortedBy ?: Patient.SortableField.PID).instanceValue(it) }
        .map { GraphQLPatient(it, authContext) }

    suspend fun patientsByExample(
        example: GraphQLPatientExample,
        authContext: GraphQLAuthContext
    ): List<GraphQLPatient> = withContext(Dispatchers.IO) {
        patientDao.searchByExample(example, authContext.mediqAuthToken)
    }
        .distinctBy { it.id }
        .map { GraphQLPatient(it, authContext) }
}
