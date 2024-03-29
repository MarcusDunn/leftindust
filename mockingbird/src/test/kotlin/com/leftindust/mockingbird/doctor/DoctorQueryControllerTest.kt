package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.person.ReadNameInfoService
import com.leftindust.mockingbird.util.DoctorMother
import com.leftindust.mockingbird.util.NameInfoMother
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain

@GraphQlTest(DoctorQueryController::class, DoctorNameInfoQueryController::class)
internal class DoctorQueryControllerTest(
    @Autowired private val graphQlTester: GraphQlTester
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readDoctorService: ReadDoctorService

    @MockkBean
    private lateinit var readNameInfoService: ReadNameInfoService

    @Test
    internal fun `check can query all basic fields`() {
        coEvery { readDoctorService.getByDoctorId(DoctorMother.Jenny.graphqlId) } returns DoctorMother.Jenny.domain
        coEvery { readNameInfoService.getByDoctorId(DoctorMother.Jenny.graphqlId) } returns NameInfoMother.JennysNameInfo.domain

        @Language("graphql")
        val query = """
            query {
                doctorsByDoctorIds(doctorIds: [{ value: "${DoctorMother.Jenny.id}" }]) {
                    id { value }
                    firstName
                    middleName
                    lastName
                    thumbnail
                    title
                    dateOfBirth
                }
            }
        """.trimIndent()

        graphQlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("doctorsByDoctorIds[0]")
            .entity(DoctorDto::class.java)
            .isEqualTo(DoctorMother.Jenny.dto)
    }

    @Test
    internal fun `check can query by range`() {
        coEvery { readDoctorService.getMany(RangeDto(0, 1)) } returns listOf(DoctorMother.Jenny.domain)
        coEvery { readNameInfoService.getByDoctorId(DoctorMother.Jenny.graphqlId) } returns NameInfoMother.JennysNameInfo.domain

        @Language("graphql")
        val query = """
            query {
                doctorsByRange(range: {from: 0, to: 1}) {
                    id { value }
                    firstName
                    middleName
                    lastName
                    thumbnail
                    title
                    dateOfBirth
                }
            }
        """.trimIndent()

        graphQlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("doctorsByRange[0]")
            .entity(DoctorDto::class.java)
            .isEqualTo(DoctorMother.Jenny.dto)
    }
}