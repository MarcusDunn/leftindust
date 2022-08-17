package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.util.DoctorMother.Jenny
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain

@GraphQlTest(DoctorQueryController::class)
internal class DoctorQueryControllerTest(
    @Autowired private val graphQlTester: GraphQlTester
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readDoctorService: ReadDoctorService

    @Test
    internal fun `check can query all basic fields`() {
        coEvery { readDoctorService.getByDoctorId(Jenny.graphqlId) } returns Jenny.entityPersisted

        @Language("graphql")
        val query = """
            query {
                doctorsByDoctorIds(doctorIds: [{ value: "${Jenny.id}" }]) {
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
            .isEqualTo(Jenny.dto)
    }

    @Test
    internal fun `check can query by range`() {
        coEvery { readDoctorService.getMany(RangeDto(0, 1)) } returns listOf(Jenny.entityPersisted)

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
            .isEqualTo(Jenny.dto)
    }
}