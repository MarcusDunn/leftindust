package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.util.SurveyLinkMother.KoosKneeSurveyLink
import com.leftindust.mockingbird.util.SurveyTemplateMother.KoosKneeSurvey
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import java.util.UUID
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain

@GraphQlTest(controllers = [SurveyLinkMutationController::class, SurveyLinkSurveyTemplateQueryController::class])
internal class SurveyLinkSurveyTemplateQueryControllerTest(
    @Autowired private val graphQlTester: GraphQlTester,
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var createSurveyLinkService: CreateSurveyLinkService

    @MockkBean
    private lateinit var readSurveyTemplateBySurveyLinkService: ReadSurveyTemplateBySurveyLinkService

    @Test
    internal fun `check can get surveyTemplate from surveyLink`() {
        coEvery { createSurveyLinkService.createSurveyLinkFromSurveyTemplateId(KoosKneeSurvey.graphqlId) } returns KoosKneeSurveyLink.domain
        coEvery { readSurveyTemplateBySurveyLinkService.getBySurveyLinkId(KoosKneeSurveyLink.graphqlId) } returns KoosKneeSurvey.domain

        @Language("graphql")
        val mutation = """
            mutation {
                createSurveyLink(surveyTemplateId: { value: "${KoosKneeSurvey.id}" }) {
                    id { value }
                    surveyTemplate {
                        id { value }
                    }
                }
            }
        """.trimIndent()

        graphQlTester.document(mutation)
            .execute()
            .errors()
            .verify()
            .path("createSurveyLink.surveyTemplate.id.value")
            .entity(UUID::class.java)
            .isEqualTo(KoosKneeSurvey.id)
            .path("createSurveyLink.id.value")
            .entity(UUID::class.java)
            .isEqualTo(KoosKneeSurveyLink.id)
    }
}