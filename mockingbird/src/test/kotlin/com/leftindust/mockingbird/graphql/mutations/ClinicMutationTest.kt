package com.leftindust.mockingbird.graphql.mutations

import com.leftindust.mockingbird.clinic.CreateClinicDao
import com.leftindust.mockingbird.clinic.UpdateClinicDao
import com.leftindust.mockingbird.clinic.Clinic
import com.leftindust.mockingbird.clinic.ClinicMutation
import com.leftindust.mockingbird.clinic.GraphQLClinic
import com.leftindust.mockingbird.clinic.GraphQLClinicEditInput
import com.leftindust.mockingbird.clinic.GraphQLClinicInput
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class ClinicMutationTest {
    private val createClinicDao = mockk<CreateClinicDao>()
    private val updateClinicDao = mockk<UpdateClinicDao>()

    @Test
    fun addClinic() {
        val clinicID = UUID.randomUUID()

        val clinic = mockk<GraphQLClinicInput>()

        val mockkClinic = mockk<Clinic>(relaxed = true) {
            every { id } returns clinicID
        }

        every { createClinicDao.addClinic(clinic, any()) } returns mockkClinic

        val clinicMutation = ClinicMutation(createClinicDao, updateClinicDao)

        val result = runBlocking { clinicMutation.addClinic(clinic, MockDataFetchingEnvironment.withDummyMediqToken) }

        assertEquals(GraphQLClinic(mockkClinic), result)
    }

    @Test
    fun editClinic() {
        val clinicID = UUID.randomUUID()

        val clinic = mockk<GraphQLClinicEditInput>()

        val mockkClinic = mockk<Clinic>(relaxed = true) {
            every { id } returns clinicID
        }

        every { updateClinicDao.editClinic(clinic, any()) } returns mockkClinic

        val clinicMutation = ClinicMutation(createClinicDao, updateClinicDao)

        val result = runBlocking { clinicMutation.editClinic(clinic, MockDataFetchingEnvironment.withDummyMediqToken) }

        assertEquals(GraphQLClinic(mockkClinic), result)
    }
}
