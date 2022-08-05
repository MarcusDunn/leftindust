package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.util.CompleteSurveyInputMother.CompleteRateThePain
import com.leftindust.mockingbird.util.CompleteSurveyMother.FilledOutKoosKneeSurvey
import com.leftindust.mockingbird.util.CompleteSurveySectionMother.CompleteHowMuchPainAreYouInSection
import com.leftindust.mockingbird.util.SurveyLinkMother
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain

@GraphQlTest(CompleteSurveyDtoMutationController::class)
internal class CompleteSurveyDtoMutationControllerTest(
    @Autowired private val graphQlTester: GraphQlTester
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var createCompleteSurveyService: CreateCompleteSurveyService

    @Test
    internal fun `check can create a survey link`() {
        coEvery { createCompleteSurveyService.createCompleteSurvey(FilledOutKoosKneeSurvey.createDto) } returns FilledOutKoosKneeSurvey.domain

        @Language("graphql")
        val mutation = """
            mutation {
                createCompleteSurvey(createCompleteSurvey: { 
                    surveyTemplateLinkId: { value: "${SurveyLinkMother.KoosKneeSurveyLink.id}" }
                    completeSurveyTemplateSections: [{
                            surveyTemplateSectionId: {value: "${CompleteHowMuchPainAreYouInSection.surveyTemplateSectionId.value}"}
                            completedSurveyInputs: [{
                                surveyTemplateSectionInputId: { value: "${CompleteRateThePain.surveyTemplateSectionInputId.value}"}
                                value: "${CompleteRateThePain.value}"
                            }]           
                        }]
                    } ) {
                    id { value }
                }
            }
        """.trimIndent()

        graphQlTester.document(mutation)
            .execute()
            .errors()
            .verify()
            .path("createCompleteSurvey")
            .entity(CompleteSurveyDto::class.java)
            .isEqualTo(FilledOutKoosKneeSurvey.dto)
    }
}