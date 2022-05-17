package com.leftindust.mockingbird.dao.entity

import com.leftindust.mockingbird.doctor.Doctor
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.person.UpdateNameInfoDto
import com.leftindust.mockingbird.patient.UpdatePatientDto
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.util.EntityStore
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.hibernate.Session
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class PatientTest {

    @Test
    fun addDoctor() {
        val patient = EntityStore.patient("PatientTest.addDoctor")

        val doctor = spyk<Doctor> {
            patients = mutableSetOf()
        }

        patient.addDoctor(doctor)

        assertEquals(doctor, patient.doctors.first().doctor)
        assertEquals(patient, doctor.patients.first().patient)
    }

    @Test
    internal fun `create by GraphQLPatientInput`() {
        val graphQLPatientInput = EntityStore.graphQLPatientInput("PatientTest.create by GraphQLPatientInput")
        val mockkSession = mockk<Session> {
            every { get(Doctor::class.java, 23L) } returns mockk {
                every { addPatient(any()) } returns mockk()
            }
            every { get(Doctor::class.java, 55L) } returns mockk {
                every { addPatient(any()) } returns mockk()
            }
        }

        Patient(graphQLPatientInput, mockkSession)
    }

    @Test
    fun setByGqlInput() {
        val patientID = UUID.randomUUID()
        val patient = EntityStore.patient("PatientTest.setByGqlInput").apply { id = patientID }

        val gqlInput = UpdatePatientDto(
            pid = PatientDto.PatientDtoId(patientID),
            nameInfo = UpdateNameInfoDto(
                firstName = "grape"
            )
        )

        patient.setByGqlInput(gqlInput, mockk())

        assertEquals(gqlInput.nameInfo!!.firstName, patient.nameInfo.firstName)
    }
}