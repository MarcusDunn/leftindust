package com.leftindust.mockingbird.dao.entity

import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.input.GraphQLDoctorEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLNameInfoEditInput
import com.leftindust.mockingbird.util.EntityStore
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
internal class DoctorTest {

    @Test
    fun addPatient() {
        val doctor = EntityStore.doctor("DoctorTest.addPatient")

        val patient = spyk<Patient>().apply {
            doctors = mutableSetOf()
        }

        doctor.addPatient(patient)

        assertEquals(patient, doctor.patients.first().patient)
        assertEquals(doctor, patient.doctors.first().doctor)
    }

    @Test
    fun setByGqlInput() {
        val doctorID = UUID.randomUUID()
        val doctor = EntityStore.doctor("DoctorTest.setByGqlInput")

        val oldMiddleName = doctor.nameInfo.middleName

        val gqlInput = GraphQLDoctorEditInput(
            did = GraphQLDoctor.ID(doctorID),
            nameInfo = GraphQLNameInfoEditInput(firstName = "new name", lastName = "new LN")
        )

        doctor.setByGqlInput(gqlInput, mockk())

        assertEquals("new name", doctor.nameInfo.firstName)
        assertEquals(oldMiddleName, doctor.nameInfo.middleName)
        assertEquals("new LN", doctor.nameInfo.lastName)
    }
}