package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.email.EmailDto
import com.leftindust.mockingbird.email.ReadEmailService
import com.leftindust.mockingbird.util.DoctorMother
import com.leftindust.mockingbird.util.EmailMother
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import java.util.UUID
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain

@GraphQlTest(controllers = [DoctorQueryController::class, DoctorEmailQueryController::class])
internal class DoctorEmailQueryControllerTest(
    @Autowired private val graphQlTester: GraphQlTester
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readEmailService: ReadEmailService

    @MockkBean
    private lateinit var readDoctorService: ReadDoctorService

    @Test
    internal fun `check can query for doctor email fields`() {
        coEvery { readDoctorService.getByDoctorId(DoctorMother.Dan.graphqlId) } returns DoctorMother.Dan.domain
        coEvery { readEmailService.getByDoctorId(DoctorMother.Dan.graphqlId) } returns listOf(EmailMother.DansEmail.domain)

        @Language("graphql")
        val query = """
            query {
                doctorsByDoctorIds(doctorIds: [{ value: "${DoctorMother.Dan.id}" }]) {
                    emails {
                        id { value }
                        type
                        email
                    }
                }
            }
        """.trimIndent()


        graphQlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("doctorsByDoctorIds[0].emails[*].id.value")
            .entity(object : ParameterizedTypeReference<List<UUID>>() {})
            .matches { it.contains(EmailMother.DansEmail.id) }
            .path("doctorsByDoctorIds[0].emails[0]")
            .entity(EmailDto::class.java)
            .isEqualTo(EmailMother.DansEmail.dto)

    }
}