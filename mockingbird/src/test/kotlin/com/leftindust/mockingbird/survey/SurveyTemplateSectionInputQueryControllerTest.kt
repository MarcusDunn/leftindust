package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.util.SurveyTemplateMother.KoosKneeSurvey
import com.leftindust.mockingbird.util.SurveyTemplateSectionInputMother.HowMuchPainAreYouInSectionInput
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

@GraphQlTest(controllers = [SurveyTemplateQueryController::class, SurveyTemplateSectionInputQueryController::class, SurveyTemplateSectionQueryController::class])
internal class SurveyTemplateSectionInputQueryControllerWebTest(
    @Autowired private val graphQlTester: GraphQlTester
) {
    @MockkBean
    private lateinit var serverHttpSecurity: SecurityWebFilterChain
    @MockkBean
    private lateinit var readSurveyTemplateSectionService: ReadSurveyTemplateSectionService
    @MockkBean
    private lateinit var readSurveyTemplateService: ReadSurveyTemplateService
    @MockkBean
    private lateinit var readSurveyTemplateSectionInputServiceImpl: ReadSurveyTemplateSectionInputService

    @Test
    internal fun `check can query inputs`() {
        val templateSurveyId = KoosKneeSurvey.dto.id
        coEvery { readSurveyTemplateService.getByTemplateSurveyId(templateSurveyId) } returns KoosKneeSurvey.domain
        coEvery { readSurveyTemplateSectionService.surveyTemplateSectionServiceBySurveySectionId(any()) } returns listOf(HowMuchPainAreYouInSection.domain)
        coEvery { readSurveyTemplateSectionInputServiceImpl.surveyTemplateSectionInputBySurveySection(any()) } returns listOf(HowMuchPainAreYouInSectionInput.domain)

        @Language("GraphQL")
        val query = """
            query {
                surveyTemplateById(surveyTemplateId: {value: "${templateSurveyId.value}"}) {
                    sections {
                        inputs {
                            id { value }
                            type
                            label
                            options
                            placeholder
                            required
                            category
                            uploadMultiple
                            uploadAccept
                        }
                    }
                }
            }
        """.trimIndent()

        graphQlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("surveyTemplateById.sections[*].inputs[*].id.value")
            .entity(object: ParameterizedTypeReference<List<UUID>>() {})
            .matches { it.contains(HowMuchPainAreYouInSectionInput.id) }
    }
}