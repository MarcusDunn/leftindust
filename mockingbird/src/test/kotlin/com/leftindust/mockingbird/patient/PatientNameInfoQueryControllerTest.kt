package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.person.ReadNameInfoService
import com.leftindust.mockingbird.util.NameInfoMother
import com.leftindust.mockingbird.util.PatientMother
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain
import java.util.*

@GraphQlTest(controllers = [PatientQueryController::class, PatientNameInfoQueryController::class])
internal class PatientNameInfoQueryControllerTest(
    @Autowired private val graphQlTester: GraphQlTester
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readPatientService: ReadPatientService

    @MockkBean
    private lateinit var readNameInfoService: ReadNameInfoService

    @Test
    internal fun `check can query for patient nameinfo fields`() {
        coEvery { readPatientService.getByPatientId(PatientMother.Dan.graphqlId) } returns PatientMother.Dan.domain
        coEvery { readNameInfoService.getByPatientId(PatientMother.Dan.graphqlId) } returns NameInfoMother.DansNameInfo.domain

        @Language("graphql")
        val query = """
            query {
                patientsByPatientId(patientIds: [{ value: "${PatientMother.Dan.id}" }]) {
                    firstName
                    lastName
                    middleName
                }
            }
        """.trimIndent()


        graphQlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("patientsByPatientId[0].firstName")
            .entity(object : ParameterizedTypeReference<String>() {})
            .matches { it.equals(NameInfoMother.DansNameInfo.firstName) }
            .path("patientsByPatientId[0].middleName")
            .entity(object : ParameterizedTypeReference<String>() {})
            .matches { it.equals(NameInfoMother.DansNameInfo.middleName) }
            .path("patientsByPatientId[0].lastName")
            .entity(object : ParameterizedTypeReference<String>() {})
            .matches { it.equals(NameInfoMother.DansNameInfo.lastName) }
    }
}