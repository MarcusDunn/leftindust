package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.util.CompleteSurveyMother
import com.leftindust.mockingbird.util.CompleteSurveySectionMother
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain

@GraphQlTest
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
    internal fun `check can query by id`() {
        coEvery { readCompleteSurveyService.completeSurveyByCompleteSurveyId(CompleteSurveyMother.FilledOutKoosKneeSurvey.graphqlId) } returns CompleteSurveyMother.FilledOutKoosKneeSurvey.domain
        coEvery { readCompleteSurveySectionService.completeSurveySectionsByCompleteSurveyId(CompleteSurveyMother.FilledOutKoosKneeSurvey.graphqlId) } returns listOf(CompleteSurveySectionMother.CompleteHowMuchPainAreYouInSection.domain)

        @Language("graphql")
        val query = """
            query {
                completeSurveyById(completeSurveyId: { value: "${CompleteSurveyMother.FilledOutKoosKneeSurvey.id}" }) {
                    sections {
                        id { value }
                    }
                }
            }
        """.trimIndent()

        graphQlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("completeSurveyById.sections")
            .entity(object : ParameterizedTypeReference<List<CompleteSurveySectionDto>>() {})
            .isEqualTo(listOf(CompleteSurveySectionMother.CompleteHowMuchPainAreYouInSection.dto))
    }
}