package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.util.ClinicMother
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain

@ExtendWith(MockKExtension::class)
internal class ClinicQueryControllerUnitTest {
    @MockK
    private lateinit var readClinicService: ReadClinicService

    @MockK
    private lateinit var clinicToClinicDtoConverter: InfallibleConverter<Clinic, ClinicDto>

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    internal fun `check returns null when service returns null`() = runTest {
        val clinicQueryController = ClinicQueryController(readClinicService, clinicToClinicDtoConverter)
        val clinicId = ClinicDto.ClinicDtoId(UUID.randomUUID())

        coEvery { readClinicService.getByClinicId(clinicId) } returns null

        assertThat(clinicQueryController.clinicByClinicId(clinicId), nullValue())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    internal fun `check returns value when service returns null`() = runTest {
        val clinicQueryController = ClinicQueryController(readClinicService, clinicToClinicDtoConverter)
        val clinicId = ClinicDto.ClinicDtoId(UUID.randomUUID())

        val clinic = mockk<Clinic>()

        coEvery { readClinicService.getByClinicId(clinicId) } returns clinic

        val clinicDto = mockk<ClinicDto>()
        every { clinicToClinicDtoConverter.convert(clinic) } returns clinicDto

        assertThat(clinicQueryController.clinicByClinicId(clinicId), equalTo(clinicDto))
    }
}

@GraphQlTest(controllers = [ClinicQueryController::class])
internal class ClinicQueryControllerWebTest(
    @Autowired private val graphqlTester: GraphQlTester,
) {
    @MockkBean
    private lateinit var serverHttpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readClinicService: ReadClinicService

    @Test
    internal fun `check returns null when no such clinic exists`() {
        val clinicDtoId = ClinicDto.ClinicDtoId(UUID.randomUUID())

        coEvery { readClinicService.getByClinicId(clinicDtoId) } returns null

        //language=graphql
        val query = """query { clinicByClinicId(clinicId: { value: "${clinicDtoId.value}" }) { name } } """
        graphqlTester.document(query)
            .execute()
            .errors()
            .satisfy { assertThat(it, hasSize(0)) }
            .path("data.clinicByClinicId")
            .valueIsNull()
    }

    @Test
    internal fun `check returns clinic with same name when a clinic exists`() {
        val clinicDtoId = ClinicDto.ClinicDtoId(UUID.randomUUID())

        val clinic = ClinicMother.clinic(id = clinicDtoId.value)

        coEvery { readClinicService.getByClinicId(clinicDtoId) } returns clinic

        //language=graphql
        val query = """query { clinicByClinicId(clinicId: { value: "${clinicDtoId.value}" }) { name } } """
        graphqlTester.document(query)
            .execute()
            .errors()
            .satisfy { assertThat(it, equalTo(emptyList())) }
            .path("data.clinicByClinicId.name")
            .entity(String::class.java)
            .isEqualTo(clinic.name)
    }
}