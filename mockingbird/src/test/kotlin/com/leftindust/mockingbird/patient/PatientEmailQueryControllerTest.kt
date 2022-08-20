package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.email.EmailDto
import com.leftindust.mockingbird.email.ReadEmailService
import com.leftindust.mockingbird.util.EmailMother
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

@GraphQlTest(controllers = [PatientQueryController::class, PatientEmailQueryController::class])
internal class PatientEmailQueryControllerTest(
    @Autowired private val graphQlTester: GraphQlTester
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readPatientService: ReadPatientService

    @MockkBean
    private lateinit var readEmailService: ReadEmailService

    @Test
    internal fun `check can query for patient emails fields`() {
        coEvery { readPatientService.getByPatientId(PatientMother.Dan.graphqlId) } returns PatientMother.Dan.entityDetached
        coEvery { readEmailService.getPatientEmails(PatientMother.Dan.graphqlId) } returns listOf(EmailMother.DansEmail.entityPersisted)

        @Language("graphql")
        val query = """
            query {
                patientsByPatientId(patientIds: [{ value: "${PatientMother.Dan.id}" }]) {
                    emails {
                        id { value }
                        email
                        type
                    }
                }
            }
        """.trimIndent()


        graphQlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("patientsByPatientId[0].emails[*].id.value")
            .entity(object : ParameterizedTypeReference<List<UUID>>() {})
            .matches { it.contains(EmailMother.DansEmail.dto.id.value) }
            .path("patientsByPatientId[0].emails[0]")
            .entity(EmailDto::class.java)
            .isEqualTo(EmailMother.DansEmail.dto)

    }
}