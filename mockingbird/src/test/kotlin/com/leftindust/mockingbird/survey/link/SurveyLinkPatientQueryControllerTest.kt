package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import com.leftindust.mockingbird.util.PatientMother
import com.leftindust.mockingbird.util.SurveyLinkMother
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain

@GraphQlTest(controllers = [SurveyLinkQueryController::class, SurveyLinkPatientQueryController::class])
internal class SurveyLinkPatientQueryControllerTest(
    @Autowired private val graphQlTester: GraphQlTester
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readSurveyLinkService: ReadSurveyLinkService

    @MockkBean
    private lateinit var readPatientService: ReadPatientService

    @Test
    internal fun `check can get a survey link by id`() {
        coEvery { readSurveyLinkService.surveyLinkBySurveyLinkId(SurveyLinkMother.KoosKneeSurveyLink.graphqlId) } returns SurveyLinkMother.KoosKneeSurveyLink.domain
        coEvery { readPatientService.getBySurveyLink(SurveyLinkMother.KoosKneeSurveyLink.graphqlId) } returns PatientMother.Dan.entityDetached

        @Language("graphql")
        val query = """
            query {
                surveyLinkById(surveyLinkId: { value: "${SurveyLinkMother.KoosKneeSurveyLink.id}" }) {
                    patient {
                        id { value }
                        firstName
                        middleName
                        lastName
                        dateOfBirth
                        insuranceNumber
                        sex
                        gender
                        ethnicity
                    }
                }
            }
        """.trimIndent()

        graphQlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("surveyLinkById.patient")
            .entity(PatientDto::class.java)
            .isEqualTo(PatientMother.Dan.dto)
    }
}