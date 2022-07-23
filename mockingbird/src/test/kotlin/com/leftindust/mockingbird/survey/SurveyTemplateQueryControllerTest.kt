package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.util.SurveyTemplateMother.`koos knee survey template`
import com.leftindust.mockingbird.util.SurveyTemplateMother.`koos knee survey template dto`
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import java.util.UUID
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain

@GraphQlTest(SurveyTemplateQueryController::class)
internal class SurveyTemplateQueryControllerWebTest(
    @Autowired private val graphQlTester: GraphQlTester,
) {
    @MockkBean
    private lateinit var serverHttpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readSurveyTemplateService: ReadSurveyTemplateService

    @Test
    internal fun `check can get a survey template by id`() {
        val templateSurveyId = `koos knee survey template dto`.id
        coEvery { readSurveyTemplateService.getByTemplateSurveyId(templateSurveyId) } returns `koos knee survey template`

        @Language("GraphQL")
        val query = """
            query {
                surveyTemplateById(surveyTemplateId: {value: "${templateSurveyId.value}"})
                    {
                        id { value }
                        title
                        subtitle
                    }
                }
        """.trimIndent()

        graphQlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("surveyTemplateById.id.value")
            .entity(UUID::class.java)
            .isEqualTo(templateSurveyId.value)
            .path("surveyTemplateById.title")
            .entity(String::class.java)
            .isEqualTo(`koos knee survey template dto`.title)
            .path("surveyTemplateById.subtitle")
            .entity(String::class.java)
            .isEqualTo(`koos knee survey template dto`.subtitle)
    }
}