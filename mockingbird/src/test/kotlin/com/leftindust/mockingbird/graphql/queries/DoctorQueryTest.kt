package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.dao.DoctorDao
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.patient.GraphQLPatient
import com.leftindust.mockingbird.util.EntityStore
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class DoctorQueryTest {
    private val doctorDao = mockk<DoctorDao>()

    @Test
    fun getDoctorsByPatient() {
        val doctorID = UUID.randomUUID()
        val patientID = UUID.randomUUID()

        val doctor = EntityStore.doctor("DoctorQueryTest.getDoctorsByPatient").apply {
            id = doctorID
        }

        val graphQLDoctor = GraphQLDoctor(doctor)

        every { doctorDao.getPatientDoctors(GraphQLPatient.ID(patientID), any()) } returns listOf(doctor)

        val doctorQuery = DoctorQuery(doctorDao)

        val result = runBlocking {
            doctorQuery.doctors(
                pid = GraphQLPatient.ID(patientID),
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }

        assertEquals(listOf(graphQLDoctor), result)
    }

    @Test
    internal fun doctors() {
        val doctorID = UUID.randomUUID()

        val doctor = EntityStore.doctor("DoctorQueryTest.doctors").apply {
            id = doctorID
        }

        val graphQLDoctor = GraphQLDoctor(doctor)

        every { doctorDao.getByDoctor(GraphQLDoctor.ID(doctorID), any()) } returns doctor

        val doctorQuery = DoctorQuery(doctorDao)

        val result = runBlocking {
            doctorQuery.doctors(
                dids = listOf(GraphQLDoctor.ID(doctorID)),
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }

        assertEquals(listOf(graphQLDoctor), result)
    }
}