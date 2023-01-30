package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.person.ReadNameInfoService
import com.leftindust.mockingbird.util.NameInfoMother
import com.leftindust.mockingbird.util.PatientMother
import com.leftindust.mockingbird.util.PatientMother.Dan
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain

@GraphQlTest(PatientQueryController::class, PatientNameInfoQueryController::class)
internal class PatientQueryControllerWebTest(
    @Autowired private val graphQlTester: GraphQlTester
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readPatientService: ReadPatientService

    @MockkBean
    private lateinit var readNameInfoService: ReadNameInfoService

    @Test
    internal fun `check can query all basic fields`() {
        coEvery { readPatientService.getByPatientId(Dan.graphqlId) } returns Dan.domain
        coEvery { readNameInfoService.getByPatientId(PatientMother.Dan.graphqlId) } returns NameInfoMother.DansNameInfo.domain

        @Language("graphql")
        val query = """
            query {
                patientsByPatientId(patientIds: [{ value: "${Dan.id}" }]) {
                    id { value }
                    firstName
                    middleName
                    lastName
                    thumbnail
                    dateOfBirth
                    insuranceNumber
                    sex
                    gender
                    ethnicity
                }
            }
        """.trimIndent()

        graphQlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("patientsByPatientId[0]")
            .entity(PatientDto::class.java)
            .isEqualTo(Dan.dto)
    }

    @Test
    internal fun `check can query by range`() {
        coEvery { readPatientService.getMany(RangeDto(0, 1)) } returns listOf(Dan.domain)
        coEvery { readNameInfoService.getByPatientId(PatientMother.Dan.graphqlId) } returns NameInfoMother.DansNameInfo.domain

        @Language("graphql")
        val query = """
            query {
                patientsByRange(range: {from: 0, to: 1}) {
                    id { value }
                    firstName
                    middleName
                    lastName
                    thumbnail
                    dateOfBirth
                    insuranceNumber
                    sex
                    gender
                    ethnicity
                }
            }
        """.trimIndent()

        graphQlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("patientsByRange[0]")
            .entity(PatientDto::class.java)
            .isEqualTo(Dan.dto)
    }
}