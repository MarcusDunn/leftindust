package com.leftindust.mockingbird.graphql.mutations

import com.leftindust.mockingbird.doctor.DoctorDao
import com.leftindust.mockingbird.doctor.Doctor
import com.leftindust.mockingbird.doctor.DoctorMutation
import com.leftindust.mockingbird.doctor.GraphQLDoctor
import com.leftindust.mockingbird.doctor.GraphQLDoctorEditInput
import com.leftindust.mockingbird.doctor.GraphQLDoctorInput
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class DoctorMutationTest {

    @Test
    fun addDoctor() {
        val doctorID = UUID.randomUUID()

        val mockkDoctor = mockk<Doctor>(relaxed = true) {
            every { id } returns doctorID
        }

        val mockkDoctorDao = mockk<DoctorDao> {
            every { addDoctor(any(), any()) } returns mockkDoctor
        }

        val mockkGraphQLDoctorInput = mockk<GraphQLDoctorInput> {
            every { user } returns null
        }

        val doctorMutation = DoctorMutation(mockkDoctorDao, mockk())

        val result = runBlocking { doctorMutation.addDoctor(mockkGraphQLDoctorInput, MockDataFetchingEnvironment.withDummyMediqToken) }

        val expected = GraphQLDoctor(mockkDoctor)

        assertEquals(expected, result)
    }

    @Test
    fun updateDoctor() {
        val doctorID = UUID.randomUUID()

        val mockkDoctor = mockk<Doctor>(relaxed = true) {
            every { id } returns doctorID
        }

        val mockkDoctorDao = mockk<DoctorDao> {
            every { editDoctor(any(), any()) } returns mockkDoctor
        }

        val mockkGraphQLDoctorInput = mockk<GraphQLDoctorEditInput>()

        val doctorMutation = DoctorMutation(mockkDoctorDao, mockk())

        val result = runBlocking { doctorMutation.editDoctor(mockkGraphQLDoctorInput, MockDataFetchingEnvironment.withDummyMediqToken) }

        val expected = GraphQLDoctor(mockkDoctor)

        assertEquals(expected, result)
    }
}