package com.leftindust.mockingbird.graphql.mutations

import com.leftindust.mockingbird.doctor.DoctorDao
import com.leftindust.mockingbird.doctor.Doctor
import com.leftindust.mockingbird.doctor.DoctorMutation
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.UpdateDoctorDto
import com.leftindust.mockingbird.doctor.CreateDoctorDto
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

        val mockkCreateDoctorDto = mockk<CreateDoctorDto> {
            every { user } returns null
        }

        val doctorMutation = DoctorMutation(mockkDoctorDao, mockk())

        val result = runBlocking { doctorMutation.addDoctor(mockkCreateDoctorDto, MockDataFetchingEnvironment.withDummyMediqToken) }

        val expected = DoctorDto(mockkDoctor)

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

        val mockkGraphQLDoctorInput = mockk<UpdateDoctorDto>()

        val doctorMutation = DoctorMutation(mockkDoctorDao, mockk())

        val result = runBlocking { doctorMutation.editDoctor(mockkGraphQLDoctorInput, MockDataFetchingEnvironment.withDummyMediqToken) }

        val expected = DoctorDto(mockkDoctor)

        assertEquals(expected, result)
    }
}