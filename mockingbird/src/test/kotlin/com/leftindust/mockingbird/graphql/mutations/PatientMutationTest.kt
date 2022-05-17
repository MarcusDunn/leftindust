package com.leftindust.mockingbird.graphql.mutations

import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.patient.CreatePatientService
import com.leftindust.mockingbird.patient.UpdatePatientService
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.UpdatePatientDto
import com.leftindust.mockingbird.patient.CreatePatientDto
import com.leftindust.mockingbird.patient.PatientMutation
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class PatientMutationTest {
    private val updatePatientService = mockk<UpdatePatientService>()
    private val createPatientService = mockk<CreatePatientService>()

    @Test
    fun addDoctorToPatient() {
        val patientID = UUID.randomUUID()
        val doctorID = UUID.randomUUID()

        val mockkPatient = mockk<Patient>(relaxed = true) {
            every { id } returns patientID
        }

        every { updatePatientService.update(any(), any()) } returns mockkPatient

        val patientMutation = PatientMutation(updatePatientService, createPatientService)

        val result = runBlocking {
            patientMutation.updatePatient(
                UpdatePatientDto(
                    pid = PatientDto.PatientDtoId(patientID),
                    doctors = listOf(DoctorDto.DoctorDtoId(doctorID))
                ),
                MockDataFetchingEnvironment.withDummyMediqToken
            )
        }

        val patientDto = PatientDto(mockkPatient)

        assertEquals(patientDto, result)
    }

    @Test
    fun updatePatient() {
        val patientID = UUID.randomUUID()

        val mockkPatient = mockk<Patient>(relaxed = true) {
            every { id } returns patientID
        }

        val mockkPatientDto = PatientDto(mockkPatient)

        every { updatePatientService.update(any(), any()) } returns mockkPatient

        val patientMutation = PatientMutation(updatePatientService, createPatientService)

        val mockkGqlPatientInput = mockk<UpdatePatientDto>()

        val result = runBlocking {
            patientMutation.updatePatient(
                mockkGqlPatientInput,
                MockDataFetchingEnvironment.withDummyMediqToken
            )
        }

        assertEquals(mockkPatientDto, result)
    }

    @Test
    fun addPatient() {
        val patientID = UUID.randomUUID()

        val mockkPatient = mockk<Patient>(relaxed = true) {
            every { id } returns patientID
        }

        val mockkPatientDto = PatientDto(mockkPatient)

        every { createPatientService.addNewPatient(any(), any()) } returns mockkPatient

        val patientMutation = PatientMutation(updatePatientService, createPatientService)

        val mockkGqlPatientInput = mockk<CreatePatientDto>()

        val result = runBlocking {
            patientMutation.addPatient(mockkGqlPatientInput, MockDataFetchingEnvironment.withDummyMediqToken)
        }

        assertEquals(mockkPatientDto, result)
    }
}