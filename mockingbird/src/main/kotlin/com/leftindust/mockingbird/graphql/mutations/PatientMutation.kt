package com.leftindust.mockingbird.graphql.mutations

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import com.leftindust.mockingbird.auth.authToken

import com.leftindust.mockingbird.dao.patient.CreatePatientDao
import com.leftindust.mockingbird.dao.patient.UpdatePatientDao
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.input.GraphQLPatientEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLPatientInput
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