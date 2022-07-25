package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.util.SurveyTemplateMother.KoosKneeSurvey
import com.leftindust.mockingbird.util.SurveyTemplateSectionMother.HowMuchPainAreYouInSection
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

@GraphQlTest(controllers = [SurveyTemplateSectionQueryController::class, SurveyTemplateQueryController::class])
internal class SurveyTemplateSectionQueryControllerWebTest(
    @Autowired private val graphQlTester: GraphQlTester
) {

    @MockkBean
    private lateinit var serverHttpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readSurveyTemplateService: ReadSurveyTemplateService

    @MockkBean
    private lateinit var readSurveyTemplateSectionService: ReadSurveyTemplateSectionService

    @Test
    internal fun `check getting sections from a survey`() {
        val templateSurveyId = KoosKneeSurvey.graphqlId
        coEvery { readSurveyTemplateService.getByTemplateSurveyId(templateSurveyId) } returns KoosKneeSurvey.domain
        coEvery { readSurveyTemplateSectionService.surveyTemplateSectionServiceBySurveySectionId(KoosKneeSurvey.graphqlId) } returns listOf(HowMuchPainAreYouInSection.domain)

        @Language("GraphQL")
        val query = """
            query {
                surveyTemplateById(surveyTemplateId: {value: "${templateSurveyId.value}"}) {
                    sections {
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
            .path("surveyTemplateById.sections[*].id.value")
            .entity(object : ParameterizedTypeReference<List<UUID>>() {})
            .isEqualTo(listOf(HowMuchPainAreYouInSection.id))
            .path("surveyTemplateById.sections[*].title")
            .entity(object : ParameterizedTypeReference<List<String>>() {})
            .isEqualTo(listOf(HowMuchPainAreYouInSection.title))
            .path("surveyTemplateById.sections[*].subtitle")
            .entity(object : ParameterizedTypeReference<List<String>>() {})
            .isEqualTo(listOf(HowMuchPainAreYouInSection.subtitle))
    }
}