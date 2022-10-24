package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.address.AddressDto
import com.leftindust.mockingbird.address.ReadAddressService
import com.leftindust.mockingbird.survey.link.ReadSurveyLinkService
import com.leftindust.mockingbird.survey.link.SurveyLink
import com.leftindust.mockingbird.survey.link.SurveyLinkDto
import com.leftindust.mockingbird.util.PatientMother
import com.leftindust.mockingbird.util.SurveyLinkMother
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

@GraphQlTest(controllers = [PatientQueryController::class, PatientSurveyLinkQueryController::class])
class PatientSurveyLinkQueryControllerTest(
    @Autowired private val graphQlTester: GraphQlTester
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readSurveyLinkService: ReadSurveyLinkService

    @MockkBean
    private lateinit var readPatientService: ReadPatientService

    @Test
    internal fun `check can query for patient assigned surveys field`() {

        coEvery { readSurveyLinkService.getByPatientId(PatientMother.Dan.graphqlId) } returns listOf(SurveyLinkMother.KoosKneeSurveyLink.domain)
        coEvery { readPatientService.getByPatientId(PatientMother.Dan.graphqlId) } returns PatientMother.Dan.domain

        @Language("graphql")
        val query = """
            query {
                patientsByPatientId(patientIds: [{ value: "${PatientMother.Dan.id}" }]) {
                    assignedSurveys {
                        id { value }
                        patient { 
                            id {
                                value                            
                            }
                        }
                    }
                }
            }
        """.trimIndent()


        graphQlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("patientsByPatientId[0].assignedSurveys[*].id.value")
            .entity(object : ParameterizedTypeReference<List<UUID>>() {})
            .matches { it.contains(SurveyLinkMother.KoosKneeSurveyLink.id) }
            .path("patientsByPatientId[0].assignedSurveys[0]")
            .entity(SurveyLinkDto::class.java)
            .isEqualTo(SurveyLinkMother.KoosKneeSurveyLink.dto)
    }
}