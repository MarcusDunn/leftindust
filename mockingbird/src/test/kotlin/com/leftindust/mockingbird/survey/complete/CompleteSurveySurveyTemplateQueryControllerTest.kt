package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.survey.template.ReadSurveyTemplateService
import com.leftindust.mockingbird.survey.template.SurveyTemplateDto
import com.leftindust.mockingbird.util.CompleteSurveyMother.FilledOutKoosKneeSurvey
import com.leftindust.mockingbird.util.SurveyTemplateMother.KoosKneeSurvey
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain

@GraphQlTest(controllers = [CompleteSurveyQueryController::class, CompleteSurveySurveyTemplateQueryController::class])
internal class CompleteSurveySurveyTemplateQueryControllerWebTest(
    @Autowired private val graphQlTester: GraphQlTester,
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readSurveyTemplateService: ReadSurveyTemplateService

    @MockkBean
    private lateinit var readCompleteSurveyService: ReadCompleteSurveyService

    @Test
    internal fun `check can get the original template`() {
        coEvery { readCompleteSurveyService.completeSurveyByCompleteSurveyId(FilledOutKoosKneeSurvey.graphqlId) } returns FilledOutKoosKneeSurvey.domain
        coEvery { readSurveyTemplateService.getByCompletedSurvey(FilledOutKoosKneeSurvey.graphqlId) } returns KoosKneeSurvey.domain

        @Language("graphql")
        val query = """
            query {
                completeSurveyById(completeSurveyId: { value: "${FilledOutKoosKneeSurvey.id}" }) {
                    surveyTemplate {
                        id { value }
                        title
                        subtitle
                    }
                }
            }
        """.trimIndent()

        graphQlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("completeSurveyById.surveyTemplate")
            .entity(SurveyTemplateDto::class.java)
            .isEqualTo(KoosKneeSurvey.dto)
    }
}