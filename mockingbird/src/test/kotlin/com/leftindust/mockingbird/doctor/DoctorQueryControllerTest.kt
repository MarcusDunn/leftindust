package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.util.DoctorMother
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
        coEvery { readDoctorService.getByDoctorId(DoctorMother.Jenny.graphqlId) } returns DoctorMother.Jenny.entityPersisted

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
}