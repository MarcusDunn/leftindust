package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.util.SurveyLinkMother.KoosKneeSurveyLink
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain

@GraphQlTest(SurveyLinkQueryController::class)
internal class SurveyLinkQueryControllerWebTest(
    @Autowired private val graphQlTester: GraphQlTester
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readSurveyLinkService: ReadSurveyLinkService

    @Test
    internal fun `check can create a survey link`() {
        coEvery { readSurveyLinkService.getBySurveyLinkId(KoosKneeSurveyLink.graphqlId) } returns KoosKneeSurveyLink.domain

        @Language("graphql")
        val query = """
            query {
                surveyLinkById(surveyLinkId: { value: "${KoosKneeSurveyLink.id}" }) {
                    id { value }
                }
            }
        """.trimIndent()

        graphQlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("surveyLinkById")
            .entity(SurveyLinkDto::class.java)
            .isEqualTo(KoosKneeSurveyLink.dto)
    }
}