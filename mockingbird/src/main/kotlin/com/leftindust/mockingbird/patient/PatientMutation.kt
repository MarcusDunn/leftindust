package com.leftindust.mockingbird.patient

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import com.leftindust.mockingbird.auth.authToken

import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class PatientMutation(
    private val updatePatientDao: UpdatePatientDao,
    private val createPatientDao: CreatePatientDao,
) : Mutation {

    @GraphQLDescription("updates a patient by their pid, only the not null fields are updated, pid MUST be defined")
    suspend fun updatePatient(
        patient: GraphQLPatientEditInput,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): GraphQLPatient = withContext(Dispatchers.IO) {
        updatePatientDao.update(patient, dataFetchingEnvironment.authToken)
    }.let(::GraphQLPatient)

    @GraphQLDescription(
        """adds a new patient and connects them to already existing doctors and contacts
        contacts and doctors default to empty lists"""
    )
    suspend fun addPatient(
        patient: GraphQLPatientInput,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): GraphQLPatient = withContext(Dispatchers.IO) {
        createPatientDao
            .addNewPatient(patient, dataFetchingEnvironment.authToken)
    }.let(::GraphQLPatient)
}