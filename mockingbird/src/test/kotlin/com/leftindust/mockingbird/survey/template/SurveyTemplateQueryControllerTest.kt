package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.util.SurveyTemplateMother.KoosKneeSurvey
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import java.util.UUID
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.core.ParameterizedTypeReference
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
        val templateSurveyId = KoosKneeSurvey.graphqlId
        coEvery { readSurveyTemplateService.getByTemplateSurveyId(templateSurveyId) } returns KoosKneeSurvey.domain

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
            .isEqualTo(KoosKneeSurvey.title)
            .path("surveyTemplateById.subtitle")
            .entity(String::class.java)
            .isEqualTo(KoosKneeSurvey.subtitle)
    }

    @Test
    internal fun `check can get by range`() {
        coEvery { readSurveyTemplateService.getByRange(RangeDto(0, 10)) } returns List(4) { KoosKneeSurvey.domain }

        @Language("GraphQL")
        val query = """
            query {
                surveyTemplateByRange(range: {from: 0, to: 10})
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
            .path("surveyTemplateByRange")
            .entity(object : ParameterizedTypeReference<List<SurveyTemplateDto>>() {})
            .isEqualTo(listOf(KoosKneeSurvey.dto, KoosKneeSurvey.dto, KoosKneeSurvey.dto, KoosKneeSurvey.dto))
    }
}