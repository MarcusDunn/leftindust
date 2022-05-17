package com.leftindust.mockingbird.graphql.mutations

import com.leftindust.mockingbird.clinic.ClinicCreationService
import com.leftindust.mockingbird.clinic.UpdateClinicService
import com.leftindust.mockingbird.clinic.Clinic
import com.leftindust.mockingbird.clinic.ClinicMutationController
import com.leftindust.mockingbird.clinic.ClinicEditDto
import com.leftindust.mockingbird.clinic.GraphQLClinicInput
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class ClinicMutationControllerTest {
    private val clinicCreationService = mockk<ClinicCreationService>()
    private val clinicUpdaterService = mockk<UpdateClinicService>()

    @Test
    fun addClinic() {
        val clinicID = UUID.randomUUID()

        val clinic = mockk<GraphQLClinicInput>()

        val mockkClinic = mockk<Clinic>(relaxed = true) {
            every { id } returns clinicID
        }

        every { clinicCreationService.addClinic(clinic, any()) } returns mockkClinic

        val clinicMutationController = ClinicMutationController(clinicCreationService, clinicUpdaterService)

        val result = runBlocking { clinicMutationController.addClinic(clinic, MockDataFetchingEnvironment.withDummyMediqToken) }

        assertEquals(GraphQLClinic(mockkClinic), result)
    }

    @Test
    fun editClinic() {
        val clinicID = UUID.randomUUID()

        val clinic = mockk<ClinicEditDto>()

        val mockkClinic = mockk<Clinic>(relaxed = true) {
            every { id } returns clinicID
        }

        every { clinicUpdaterService.editClinic(clinic, any()) } returns mockkClinic

        val clinicMutationController = ClinicMutationController(clinicCreationService, clinicUpdaterService)

        val result = runBlocking { clinicMutationController.editClinic(clinic, MockDataFetchingEnvironment.withDummyMediqToken) }

        assertEquals(GraphQLClinic(mockkClinic), result)
    }
}
