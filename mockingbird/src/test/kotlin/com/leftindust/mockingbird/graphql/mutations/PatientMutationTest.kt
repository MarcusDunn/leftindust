package com.leftindust.mockingbird.graphql.mutations

import com.leftindust.mockingbird.dao.entity.Patient
import com.leftindust.mockingbird.dao.patient.CreatePatientDao
import com.leftindust.mockingbird.dao.patient.UpdatePatientDao
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.input.GraphQLPatientEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLPatientInput
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class PatientMutationTest {
    private val updatePatientDao = mockk<UpdatePatientDao>()
    private val createPatientDao = mockk<CreatePatientDao>()

    @Test
    fun addDoctorToPatient() {
        val patientID = UUID.randomUUID()
        val doctorID = UUID.randomUUID()

        val mockkPatient = mockk<Patient>(relaxed = true) {
            every { id } returns patientID
        }

        every { updatePatientDao.update(any(), any()) } returns mockkPatient

        val patientMutation = PatientMutation(updatePatientDao, createPatientDao)

        val result = runBlocking {
            patientMutation.updatePatient(
                GraphQLPatientEditInput(
                    pid = GraphQLPatient.ID(patientID),
                    doctors = listOf(GraphQLDoctor.ID(doctorID))
                ),
                MockDataFetchingEnvironment.withDummyMediqToken
            )
        }

        val graphQLPatient = GraphQLPatient(mockkPatient)

        assertEquals(graphQLPatient, result)
    }

    @Test
    fun updatePatient() {
        val patientID = UUID.randomUUID()

        val mockkPatient = mockk<Patient>(relaxed = true) {
            every { id } returns patientID
        }

        val mockkGraphQLPatient = GraphQLPatient(mockkPatient)

        every { updatePatientDao.update(any(), any()) } returns mockkPatient

        val patientMutation = PatientMutation(updatePatientDao, createPatientDao)

        val mockkGqlPatientInput = mockk<GraphQLPatientEditInput>()

        val result = runBlocking {
            patientMutation.updatePatient(
                mockkGqlPatientInput,
                MockDataFetchingEnvironment.withDummyMediqToken
            )
        }

        assertEquals(mockkGraphQLPatient, result)
    }

    @Test
    fun addPatient() {
        val patientID = UUID.randomUUID()

        val mockkPatient = mockk<Patient>(relaxed = true) {
            every { id } returns patientID
        }

        val mockkGraphQLPatient = GraphQLPatient(mockkPatient)

        every { createPatientDao.addNewPatient(any(), any()) } returns mockkPatient

        val patientMutation = PatientMutation(updatePatientDao, createPatientDao)

        val mockkGqlPatientInput = mockk<GraphQLPatientInput>()

        val result = runBlocking {
            patientMutation.addPatient(mockkGqlPatientInput, MockDataFetchingEnvironment.withDummyMediqToken)
        }

        assertEquals(mockkGraphQLPatient, result)
    }
}