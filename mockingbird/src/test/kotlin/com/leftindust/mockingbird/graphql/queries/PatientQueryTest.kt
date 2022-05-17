package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.patient.ReadPatientService
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.patient.PatientExampleDto
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
    private val readPatientService = mockk<ReadPatientService>()

    @Test
    fun patient() {
        val patientID = UUID.randomUUID()

        val mockkPatient = mockk<Patient>(relaxed = true) {
            every { id } returns patientID
        }
        every { readPatientService.getPatientsByPids(listOf(PatientDto.PatientDtoId(patientID)), any()) } returns listOf(
            mockkPatient
        )

        val patientDto = PatientDto(mockkPatient)
        val patientQuery = PatientQuery(readPatientService)
        val result = runBlocking { patientQuery.patientsByPatientId(listOf(PatientDto.PatientDtoId(patientID)), dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken) }
        assertEquals(listOf(patientDto), result)
    }

    @Test
    fun patients() {
        val patientID = UUID.randomUUID()

        val mockkPatient = mockk<Patient>(relaxed = true) {
            every { id } returns patientID
        }

        every { readPatientService.getMany(any(), any(), any()) } returns listOf(
            mockkPatient,
            mockkPatient,
            mockkPatient,
            mockkPatient,
            mockkPatient
        )
        val patientQuery = PatientQuery(readPatientService)
        val patientDto = PatientDto(mockkPatient)
        val result = runBlocking { patientQuery.patientsByRange(RangeDto(0, 5), dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken) }
        assertEquals((0 until 5).map { patientDto }, result)
    }

    @Test
    fun patientsByExample() {
        val patientID = UUID.randomUUID()

        val mockkPatient = mockk<Patient>(relaxed = true) {
            every { id } returns patientID
        }

        every { readPatientService.searchByExample(any(), any()) } returns listOf(
            mockkPatient,
            mockkPatient,
            mockkPatient,
            mockkPatient,
            mockkPatient
        )
        val patientQuery = PatientQuery(readPatientService)
        val patientDto = PatientDto(mockkPatient)
        val result = runBlocking {
            patientQuery.patientsByExample(
                PatientExampleDto(
                    firstName = CaseAgnosticStringFilter(eq = "Marcus", strict = true),
                    strict = true
                ),
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }
        assertEquals((0 until 1).map { patientDto }, result)
    }
}