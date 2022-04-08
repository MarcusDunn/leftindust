package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.patient.ReadPatientDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired

data class GraphQLFormData(
    val data: String,
    private val patient: GraphQLPatient.ID,
    private val authContext: GraphQLAuthContext
) {
    suspend fun patient(
        @Autowired @GraphQLIgnore patientDao: ReadPatientDao
    ): GraphQLPatient = withContext(Dispatchers.IO) {
        patientDao.getByPID(patient, authContext.mediqAuthToken)
    }.let { GraphQLPatient(it, authContext) }
}