package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.util.CompleteSurveySectionInputMother.FilledOutHowBadIsThePainWhenIPokeIt
import com.leftindust.mockingbird.util.CompleteSurveyMother.FilledOutKoosKneeSurvey
import com.leftindust.mockingbird.util.CompleteSurveySectionMother.CompleteHowMuchPainAreYouInSection
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain
import java.util.*

@GraphQlTest(CompleteSurveyDtoMutationController::class)
internal class  CompleteSurveyDtoMutationControllerTest(
    @Autowired private val graphQlTester: GraphQlTester
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var createCompleteSurveyService: CreateCompleteSurveyService

    @Test
    internal fun `check can create a survey link`() {
        coEvery { createCompleteSurveyService.createCompleteSurvey(any()) } returns FilledOutKoosKneeSurvey.domain

        @Language("graphql")
        val mutation = """
            mutation {
                createCompleteSurvey(createCompleteSurvey: { 
                    surveyLinkId: { value: "${FilledOutKoosKneeSurvey.surveyLinkId.value}" }
                    completeSurveyTemplateSections: [{
                            surveyTemplateSectionId: {
                                value: "${CompleteHowMuchPainAreYouInSection.surveyTemplateSectionId.value}"
                            }
                            completedSurveyInputs: [{
                                surveyTemplateSectionInputId: { value: "${FilledOutHowBadIsThePainWhenIPokeIt.surveyTemplateSectionInputId.value}"}
                                type: String
                                stringInput: "${FilledOutHowBadIsThePainWhenIPokeIt.createDto.stringInput}"
                            }]
                        }]
                    }) {
                    id { value }
                }
            }
        """.trimIndent()

        graphQlTester.document(mutation)
            .execute()
            .errors()
            .verify()
            .path("createCompleteSurvey.id.value")
            .entity(UUID::class.java)
            .isEqualTo(FilledOutKoosKneeSurvey.dto.id.value)
    }
}