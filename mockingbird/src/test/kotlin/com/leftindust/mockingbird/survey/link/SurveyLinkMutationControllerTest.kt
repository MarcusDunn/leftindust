package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.util.SurveyLinkMother.KoosKneeSurveyLink
import com.leftindust.mockingbird.util.SurveyTemplateMother.KoosKneeSurvey
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain


@GraphQlTest(SurveyLinkMutationController::class)
internal class SurveyLinkMutationControllerWebTest(
    @Autowired private val graphQlTester: GraphQlTester
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var createSurveyLinkService: CreateSurveyLinkService

    @Test
    internal fun `check can create a survey link`() {
        coEvery { createSurveyLinkService.createSurveyLinkFromSurveyTemplateId(KoosKneeSurvey.graphqlId) } returns KoosKneeSurveyLink.domain

        @Language("graphql")
        val mutation = """
            mutation {
                createSurveyLink(surveyTemplateId: { value: "${KoosKneeSurvey.id}" }) {
                    id { value }
                }
            }
        """.trimIndent()

        graphQlTester.document(mutation)
            .execute()
            .errors()
            .verify()
            .path("createSurveyLink")
            .entity(SurveyLinkDto::class.java)
            .isEqualTo(KoosKneeSurveyLink.dto)
    }
}