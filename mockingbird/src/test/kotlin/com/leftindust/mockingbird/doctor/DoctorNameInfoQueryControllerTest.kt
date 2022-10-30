package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.person.ReadNameInfoService
import com.leftindust.mockingbird.util.DoctorMother
import com.leftindust.mockingbird.util.NameInfoMother
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain

@GraphQlTest(controllers = [DoctorQueryController::class, DoctorNameInfoQueryController::class])
internal class DoctorNameInfoQueryControllerTest(
    @Autowired private val graphQlTester: GraphQlTester
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readDoctorService: ReadDoctorService

    @MockkBean
    private lateinit var readNameInfoService: ReadNameInfoService

    @Test
    internal fun `check can query for patient nameinfo fields`() {
        coEvery { readDoctorService.getByDoctorId(DoctorMother.Jenny.graphqlId) } returns DoctorMother.Jenny.domain
        coEvery { readNameInfoService.getByDoctorId(DoctorMother.Jenny.graphqlId) } returns NameInfoMother.JennysNameInfo.domain

        @Language("graphql")
        val query = """
            query {
                doctorsByDoctorIds(doctorIds: [{ value: "${DoctorMother.Jenny.id}" }]) {
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
            .path("doctorsByDoctorIds[0].firstName")
            .entity(object : ParameterizedTypeReference<String>() {})
            .matches { it.equals(NameInfoMother.JennysNameInfo.firstName) }
            .path("doctorsByDoctorIds[0].middleName")
            .entity(object : ParameterizedTypeReference<String>() {})
            .matches { it.equals(NameInfoMother.JennysNameInfo.middleName) }
            .path("doctorsByDoctorIds[0].lastName")
            .entity(object : ParameterizedTypeReference<String>() {})
            .matches { it.equals(NameInfoMother.JennysNameInfo.lastName) }
    }
}