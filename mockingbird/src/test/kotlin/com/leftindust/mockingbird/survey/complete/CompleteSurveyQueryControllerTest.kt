package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.util.CompleteSurveyMother.FilledOutKoosKneeSurvey
import com.leftindust.mockingbird.util.CompleteSurveySectionInputMother.FilledOutHowBadIsThePainWhenIPokeIt
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

@GraphQlTest(CompleteSurveyQueryController::class)
internal class CompleteSurveyQueryControllerWebTest(
    @Autowired private val graphQlTester: GraphQlTester,
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readCompleteSurveyService: ReadCompleteSurveyService

    @MockkBean
    private lateinit var readCompleteSurveySectionService: ReadCompleteSurveySectionService

    @MockkBean
    private lateinit var readCompleteSurveySectionInputService: ReadCompleteSurveySectionInputService

    @Test
    internal fun `check can query by id`() {
        coEvery { readCompleteSurveyService.completeSurveyByCompleteSurveyId(FilledOutKoosKneeSurvey.graphqlId) } returns FilledOutKoosKneeSurvey.domain
        coEvery { readCompleteSurveySectionService.completeSurveySectionsByCompleteSurveyId(FilledOutKoosKneeSurvey.graphqlId) } returns listOf(
            CompleteHowMuchPainAreYouInSection.domain
        )
        coEvery {
            readCompleteSurveySectionInputService.completeSurveySectionInputByCompleteSurveySectionId(
                CompleteHowMuchPainAreYouInSection.graphqlId
            )
        } returns listOf(
            FilledOutHowBadIsThePainWhenIPokeIt.domain
        )

        @Language("graphql")
        val query = """
            query {
                completeSurveyById(completeSurveyId: { value: "${FilledOutKoosKneeSurvey.id}" }) {
                    id { value }
                }
            }
        """.trimIndent()

        graphQlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("completeSurveyById")
            .entity(CompleteSurveyDto::class.java)
            .isEqualTo(FilledOutKoosKneeSurvey.dto)
    }

    @Test
    internal fun `check can query by range`() {
        coEvery { readCompleteSurveyService.getMany(RangeDto(0, 1)) } returns listOf(FilledOutKoosKneeSurvey.domain)

        @Language("graphql")
        val query = """
            query {
                completeSurveyByRange(range: {from: 0 to : 1}) {
                    id { value }
                }
            }
        """.trimIndent()

        graphQlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("completeSurveyByRange[0].id.value")
            .entity(UUID::class.java)
            .isEqualTo(FilledOutKoosKneeSurvey.dto.id.value)
    }
}