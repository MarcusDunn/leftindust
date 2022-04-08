package com.leftindust.mockingbird.graphql.mutations

import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.entity.Patient
import com.leftindust.mockingbird.dao.patient.CreatePatientDao
import com.leftindust.mockingbird.dao.patient.UpdatePatientDao
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.input.GraphQLPatientEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLPatientInput
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PatientMutationTest {
    private val authContext = mockk<GraphQLAuthContext>()
    private val updatePatientDao = mockk<UpdatePatientDao>()
    private val createPatientDao = mockk<CreatePatientDao>()

    @Test
    fun addDoctorToPatient() {
        val patientID = UUID.randomUUID()
        val doctorID = UUID.randomUUID()

        val mockkPatient = mockk<Patient>(relaxed = true) {
            every { id } returns patientID
        }

        every { authContext.mediqAuthToken } returns mockk()

        every { updatePatientDao.update(any(), any()) } returns mockkPatient

        val patientMutation = PatientMutation(updatePatientDao, createPatientDao)

        val result = runBlocking {
            patientMutation.updatePatient(
                GraphQLPatientEditInput(
                    pid = GraphQLPatient.ID(patientID),
                    doctors = listOf(GraphQLDoctor.ID(doctorID))
                ),
                authContext
            )
        }

        val graphQLPatient = GraphQLPatient(mockkPatient, authContext)

        assertEquals(graphQLPatient, result)
    }

    @Test
    fun updatePatient() {
        val patientID = UUID.randomUUID()

        val mockkPatient = mockk<Patient>(relaxed = true) {
            every { id } returns patientID
        }

        every { authContext.mediqAuthToken } returns mockk()

        val mockkGraphQLPatient = GraphQLPatient(mockkPatient, authContext)

        every { updatePatientDao.update(any(), any()) } returns mockkPatient

        val patientMutation = PatientMutation(updatePatientDao, createPatientDao)

        val mockkGqlPatientInput = mockk<GraphQLPatientEditInput>()

        val result = runBlocking { patientMutation.updatePatient(mockkGqlPatientInput, authContext) }

        assertEquals(mockkGraphQLPatient, result)
    }

    @Test
    fun addPatient() {
        val patientID = UUID.randomUUID()

        val mockkPatient = mockk<Patient>(relaxed = true) {
            every { id } returns patientID
        }

        every { authContext.mediqAuthToken } returns mockk()

        val mockkGraphQLPatient = GraphQLPatient(mockkPatient, authContext)

        every { createPatientDao.addNewPatient(any(), any()) } returns mockkPatient

        val patientMutation = PatientMutation(updatePatientDao, createPatientDao)

        val mockkGqlPatientInput = mockk<GraphQLPatientInput>()

        val result = runBlocking { patientMutation.addPatient(mockkGqlPatientInput, authContext) }

        assertEquals(mockkGraphQLPatient, result)
    }
}