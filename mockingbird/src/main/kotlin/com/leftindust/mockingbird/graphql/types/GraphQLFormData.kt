package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.patient.ReadPatientDao
import com.leftindust.mockingbird.patient.GraphQLPatient
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired

data class GraphQLFormData(
    val data: String,
    private val patient: GraphQLPatient.ID,
) {
    suspend fun patient(
        @Autowired @GraphQLIgnore patientDao: ReadPatientDao,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): GraphQLPatient = withContext(Dispatchers.IO) {
        patientDao.getByPID(patient, dataFetchingEnvironment.authToken)
    }.let(::GraphQLPatient)
}