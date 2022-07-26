package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.util.SurveySectionTemplateCalculationMother.FirstCalculation
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


@GraphQlTest(controllers = [SurveyTemplateQueryController::class, SurveyTemplateCalculationQueryController::class])
internal class SurveyTemplateCalculationQueryControllerWebTest(
    @Autowired private val graphQlTester: GraphQlTester,
) {
    @MockkBean
    private lateinit var serverHttpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readSurveyTemplateService: ReadSurveyTemplateService

    @MockkBean
    private lateinit var readSurveyTemplateCalculationService: ReadSurveyTemplateCalculationService

    @Test
    internal fun `check can query calculations`() {
        coEvery { readSurveyTemplateService.getByTemplateSurveyId(KoosKneeSurvey.graphqlId) } returns KoosKneeSurvey.domain
        coEvery { readSurveyTemplateCalculationService.surveyTemplateCalculationBySurveyTemplateId(KoosKneeSurvey.graphqlId) } returns listOf(FirstCalculation.domain)

        @Language("GraphQL")
        val query = """
            query {
                surveyTemplateById(surveyTemplateId: {value: "${KoosKneeSurvey.id}"}) {
                    calculations {
                        id { value }
                        index
                        label
                        inputType
                        showOnComplete
                        calculation
                    }
                }
            }
        """.trimMargin()

        graphQlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("surveyTemplateById.calculations[*].id.value")
            .entity(object : ParameterizedTypeReference<List<UUID>>() {})
            .matches { it.contains(FirstCalculation.id) }
            .path("surveyTemplateById.calculations[0]")
            .entity(SurveyTemplateCalculationDto::class.java)
            .isEqualTo(FirstCalculation.dto)
    }
}