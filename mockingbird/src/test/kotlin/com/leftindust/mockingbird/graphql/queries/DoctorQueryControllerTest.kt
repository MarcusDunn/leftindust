package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.doctor.DoctorDao
import com.leftindust.mockingbird.doctor.DoctorQueryController
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.util.EntityStore
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class DoctorQueryControllerTest {
    private val doctorDao = mockk<DoctorDao>()

    @Test
    fun getDoctorsByPatient() {
        val doctorID = UUID.randomUUID()
        val patientID = UUID.randomUUID()

        val doctor = EntityStore.doctor("DoctorQueryTest.getDoctorsByPatient").apply {
            id = doctorID
        }

        val doctorDto = DoctorDto(doctor)

        every { doctorDao.getPatientDoctors(PatientDto.PatientDtoId(patientID), any()) } returns listOf(doctor)

        val doctorQueryController = DoctorQueryController(doctorDao)

        val result = runBlocking {
            doctorQueryController.doctors(
                pid = PatientDto.PatientDtoId(patientID),
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }

        assertEquals(listOf(doctorDto), result)
    }

    @Test
    internal fun doctors() {
        val doctorID = UUID.randomUUID()

        val doctor = EntityStore.doctor("DoctorQueryTest.doctors").apply {
            id = doctorID
        }

        val doctorDto = DoctorDto(doctor)

        every { doctorDao.getByDoctor(DoctorDto.DoctorDtoId(doctorID), any()) } returns doctor

        val doctorQueryController = DoctorQueryController(doctorDao)

        val result = runBlocking {
            doctorQueryController.doctors(
                dids = listOf(DoctorDto.DoctorDtoId(doctorID)),
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }

        assertEquals(listOf(doctorDto), result)
    }
}