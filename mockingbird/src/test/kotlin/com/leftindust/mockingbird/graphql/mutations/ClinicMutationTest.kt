package com.leftindust.mockingbird.graphql.mutations

import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.clinic.CreateClinicDao
import com.leftindust.mockingbird.dao.clinic.UpdateClinicDao
import com.leftindust.mockingbird.dao.entity.Clinic
import com.leftindust.mockingbird.graphql.types.GraphQLClinic
import com.leftindust.mockingbird.graphql.types.input.GraphQLClinicEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLClinicInput
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ClinicMutationTest {
    private val createClinicDao = mockk<CreateClinicDao>()
    private val updateClinicDao = mockk<UpdateClinicDao>()

    @Test
    fun addClinic() {
        val clinicID = UUID.randomUUID()

        val authContext = mockk<GraphQLAuthContext> {
            every { mediqAuthToken } returns mockk()
        }

        val clinic = mockk<GraphQLClinicInput>()

        val mockkClinic = mockk<Clinic>(relaxed = true) {
            every { id } returns clinicID
        }

        every { createClinicDao.addClinic(clinic, authContext.mediqAuthToken) } returns mockkClinic

        val clinicMutation = ClinicMutation(createClinicDao, updateClinicDao)

        val result = runBlocking { clinicMutation.addClinic(clinic, authContext) }

        assertEquals(GraphQLClinic(mockkClinic,  authContext), result)
    }

    @Test
    fun editClinic() {
        val clinicID = UUID.randomUUID()

        val authContext = mockk<GraphQLAuthContext> {
            every { mediqAuthToken } returns mockk()
        }

        val clinic = mockk<GraphQLClinicEditInput>()

        val mockkClinic = mockk<Clinic>(relaxed = true) {
            every { id } returns clinicID
        }

        every { updateClinicDao.editClinic(clinic, authContext.mediqAuthToken) } returns mockkClinic

        val clinicMutation = ClinicMutation(createClinicDao, updateClinicDao)

        val result = runBlocking { clinicMutation.editClinic(clinic, authContext) }

        assertEquals(GraphQLClinic(mockkClinic, authContext), result)
    }
}
