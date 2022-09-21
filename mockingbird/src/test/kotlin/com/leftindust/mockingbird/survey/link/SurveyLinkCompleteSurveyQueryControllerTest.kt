package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.survey.complete.CompleteSurveyDto
import com.leftindust.mockingbird.survey.complete.ReadCompleteSurveyService
import com.leftindust.mockingbird.util.CompleteSurveyMother.FilledOutKoosKneeSurvey
import com.leftindust.mockingbird.util.SurveyLinkMother.KoosKneeSurveyLink
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain

@GraphQlTest(controllers = [SurveyLinkQueryController::class, SurveyLinkCompleteSurveyQueryController::class])
internal class SurveyLinkCompleteSurveyQueryControllerTest(
    @Autowired private val graphQlTester: GraphQlTester
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readSurveyLinkService: ReadSurveyLinkService

    @MockkBean
    private lateinit var readCompleteSurveyService: ReadCompleteSurveyService

    @Test
    internal fun `check can get a survey link by id`() {
        coEvery { readSurveyLinkService.surveyLinkBySurveyLinkId(KoosKneeSurveyLink.graphqlId) } returns KoosKneeSurveyLink.domain
        coEvery { readCompleteSurveyService.getBySurveyLink(KoosKneeSurveyLink.graphqlId) } returns FilledOutKoosKneeSurvey.domain

        @Language("graphql")
        val query = """
            query {
                surveyLinkById(surveyLinkId: { value: "${KoosKneeSurveyLink.id}" }) {
                    completedSurvey {
                        id { value }
                        sections {
                            id { value }
                            inputs {
                               id { value }
                               ... on CompleteSurveySectionStringInput {
                                  id { value }
                                  string
                               }              
                               ... on CompleteSurveySectionNumberInput {
                                  id { value }
                                  number
                               }    
                               ... on CompleteSurveySectionStringArrayInput {
                                  id { value }
                                  stringArray
                               }    
                               ... on CompleteSurveySectionNumberArrayInput {
                                  id { value }
                                  numberArray
                               }    
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
            .path("surveyLinkById.completedSurvey")
            .entity(CompleteSurveyDto::class.java)
            .isEqualTo(FilledOutKoosKneeSurvey.dto)
    }
}