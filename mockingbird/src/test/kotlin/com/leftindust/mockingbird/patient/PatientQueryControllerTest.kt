package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.util.PatientMother.Dan
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain

@GraphQlTest(PatientQueryController::class)
internal class PatientQueryControllerWebTest(
    @Autowired private val graphQlTester: GraphQlTester
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readPatientService: ReadPatientService

    @Test
    internal fun `check can query all basic fields`() {
        coEvery { readPatientService.getByPatientId(Dan.graphqlid) } returns Dan.entityPersisted

        @Language("graphql")
        val mutation = """
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

        graphQlTester.document(mutation)
            .execute()
            .errors()
            .verify()
            .path("patientsByPatientId[0]")
            .entity(PatientDto::class.java)
            .isEqualTo(Dan.dto)
    }
}