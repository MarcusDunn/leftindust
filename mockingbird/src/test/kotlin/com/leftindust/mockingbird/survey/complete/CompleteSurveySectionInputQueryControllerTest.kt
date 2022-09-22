package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.util.CompleteSurveyMother.FilledOutKoosKneeSurvey
import com.leftindust.mockingbird.util.CompleteSurveySectionInputMother.FilledOutHowBadIsThePainWhenIPokeIt
import com.leftindust.mockingbird.util.CompleteSurveySectionMother
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain
import java.util.UUID

@GraphQlTest(controllers = [CompleteSurveyQueryController::class, CompleteSurveySectionQueryController::class, CompleteSurveySectionInputQueryController::class])
internal class CompleteSurveySectionInputQueryControllerWebTest(
    @Autowired private val graphQlTester: GraphQlTester,
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readCompleteSurveyService: ReadCompleteSurveyService

    @MockkBean
    private lateinit var readCompleteSurveySectionService: ReadCompleteSurveySectionService

    @MockkBean
    private lateinit var readCompleteSurveySectionInputService: ReadCompleteSurveySectionInputService

    @Test
    internal fun `check can query for section inputs by id`() {
        coEvery { readCompleteSurveyService.completeSurveyByCompleteSurveyId(FilledOutKoosKneeSurvey.graphqlId) } returns FilledOutKoosKneeSurvey.domain
        coEvery { readCompleteSurveySectionService.completeSurveySectionsByCompleteSurveyId(FilledOutKoosKneeSurvey.graphqlId) } returns listOf(
            CompleteSurveySectionMother.CompleteHowMuchPainAreYouInSection.domain
        )
        coEvery {
            readCompleteSurveySectionInputService.completeSurveySectionInputByCompleteSurveySectionId(
                CompleteSurveySectionMother.CompleteHowMuchPainAreYouInSection.graphqlId
            )
        } returns listOf(
            FilledOutHowBadIsThePainWhenIPokeIt.domain
        )

        @Language("graphql")
        val query = """
            query {
                completeSurveyById(
                    completeSurveyId: { value: "${FilledOutKoosKneeSurvey.id}" }) {
                    sections {
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
        """.trimIndent()

        graphQlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("completeSurveyById.sections[0].inputs[0].string")
            .entity(String::class.java)
            .isEqualTo(FilledOutHowBadIsThePainWhenIPokeIt.value)
    }
}