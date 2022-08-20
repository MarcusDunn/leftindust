package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.phone.PhoneDto
import com.leftindust.mockingbird.phone.ReadPhoneService
import com.leftindust.mockingbird.util.PatientMother
import com.leftindust.mockingbird.util.PhoneMother
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

@GraphQlTest(controllers = [PatientQueryController::class, PatientPhoneQueryController::class])
internal class PatientPhoneQueryControllerTest(
    @Autowired private val graphQlTester: GraphQlTester
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readPatientService: ReadPatientService

    @MockkBean
    private lateinit var readPhoneService: ReadPhoneService

    @Test
    internal fun `check can query for patient phone fields`() {
        coEvery { readPatientService.getByPatientId(PatientMother.Dan.graphqlId) } returns PatientMother.Dan.entityDetached
        coEvery { readPhoneService.getByPatientId(PatientMother.Dan.graphqlId) } returns listOf(PhoneMother.DansCell.entityPersisted)

        @Language("graphql")
        val query = """
            query {
                patientsByPatientId(patientIds: [{ value: "${PatientMother.Dan.id}" }]) {
                    phoneNumbers {
                        id { value }
                        number
                        type
                    }
                }
            }
        """.trimIndent()


        graphQlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("patientsByPatientId[0].phoneNumbers[*].id.value")
            .entity(object : ParameterizedTypeReference<List<UUID>>() {})
            .matches { it.contains(PhoneMother.DansCell.dto.id.value) }
            .path("patientsByPatientId[0].phoneNumbers[0]")
            .entity(PhoneDto::class.java)
            .isEqualTo(PhoneMother.DansCell.dto)

    }
}