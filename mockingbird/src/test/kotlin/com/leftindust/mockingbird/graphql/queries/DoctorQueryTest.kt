package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.DoctorDao
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.util.EntityStore
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DoctorQueryTest {
    private val doctorDao = mockk<DoctorDao>()
    private val authContext = mockk<GraphQLAuthContext>()

    @Test
    fun getDoctorsByPatient() {
        val doctorID = UUID.randomUUID()
        val patientID = UUID.randomUUID()

        val doctor = EntityStore.doctor("DoctorQueryTest.getDoctorsByPatient").apply {
            id = doctorID
        }

        every { authContext.mediqAuthToken } returns mockk()

        val graphQLDoctor = GraphQLDoctor(doctor, authContext)

        every { doctorDao.getPatientDoctors(GraphQLPatient.ID(patientID), authContext.mediqAuthToken) } returns listOf(doctor)

        val doctorQuery = DoctorQuery(doctorDao)

        val result = runBlocking { doctorQuery.doctors(pid = GraphQLPatient.ID(patientID), authContext = authContext) }

        assertEquals(listOf(graphQLDoctor), result)
    }

    @Test
    internal fun doctors() {
        val doctorID = UUID.randomUUID()

        val doctor = EntityStore.doctor("DoctorQueryTest.doctors").apply {
            id = doctorID
        }

        every { authContext.mediqAuthToken } returns mockk()

        val graphQLDoctor = GraphQLDoctor(doctor, authContext)

        every { doctorDao.getByDoctor(GraphQLDoctor.ID(doctorID), any()) } returns doctor

        val doctorQuery = DoctorQuery(doctorDao)

        val result = runBlocking { doctorQuery.doctors(dids = listOf(GraphQLDoctor.ID(doctorID)), authContext = authContext) }

        assertEquals(listOf(graphQLDoctor), result)
    }
}