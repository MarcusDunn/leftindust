package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.patient.ReadPatientDao
import com.leftindust.mockingbird.patient.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput
import com.leftindust.mockingbird.graphql.types.search.example.GraphQLPatientExample
import com.leftindust.mockingbird.graphql.types.search.filter.CaseAgnosticStringFilter
import com.leftindust.mockingbird.patient.PatientQuery
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PatientQueryTest {
    private val readPatientDao = mockk<ReadPatientDao>()

    @Test
    fun patient() {
        val patientID = UUID.randomUUID()

        val mockkPatient = mockk<Patient>(relaxed = true) {
            every { id } returns patientID
        }
        every { readPatientDao.getPatientsByPids(listOf(GraphQLPatient.ID(patientID)), any()) } returns listOf(
            mockkPatient
        )

        val graphQLPatient = GraphQLPatient(mockkPatient)
        val patientQuery = PatientQuery(readPatientDao)
        val result = runBlocking { patientQuery.patientsByPid(listOf(GraphQLPatient.ID(patientID)), dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken) }
        assertEquals(listOf(graphQLPatient), result)
    }

    @Test
    fun patients() {
        val patientID = UUID.randomUUID()

        val mockkPatient = mockk<Patient>(relaxed = true) {
            every { id } returns patientID
        }

        every { readPatientDao.getMany(any(), any(), any()) } returns listOf(
            mockkPatient,
            mockkPatient,
            mockkPatient,
            mockkPatient,
            mockkPatient
        )
        val patientQuery = PatientQuery(readPatientDao)
        val graphQLPatient = GraphQLPatient(mockkPatient)
        val result = runBlocking { patientQuery.patientsByRange(GraphQLRangeInput(0, 5), dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken) }
        assertEquals((0 until 5).map { graphQLPatient }, result)
    }

    @Test
    fun patientsByExample() {
        val patientID = UUID.randomUUID()

        val mockkPatient = mockk<Patient>(relaxed = true) {
            every { id } returns patientID
        }

        every { readPatientDao.searchByExample(any(), any()) } returns listOf(
            mockkPatient,
            mockkPatient,
            mockkPatient,
            mockkPatient,
            mockkPatient
        )
        val patientQuery = PatientQuery(readPatientDao)
        val graphQLPatient = GraphQLPatient(mockkPatient)
        val result = runBlocking {
            patientQuery.patientsByExample(
                GraphQLPatientExample(
                    firstName = CaseAgnosticStringFilter(eq = "Marcus", strict = true),
                    strict = true
                ),
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }
        assertEquals((0 until 1).map { graphQLPatient }, result)
    }
}