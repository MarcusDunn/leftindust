package com.leftindust.mockingbird.survey.template

import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain
import java.util.UUID

internal class SurveyTemplateMutationControllerUnitTest {

}

@GraphQlTest(controllers = [SurveyTemplateMutationController::class])
internal class SurveyTemplateMutationControllerWebTest(
    @Autowired private val graphQlTester: GraphQlTester,
) {
    @MockkBean
    private lateinit var serverHttpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var createSurveyTemplateService: CreateSurveyTemplateService

    @Test
    internal fun `check can get sections after adding the smallest valid mutation`() {
        val mutation = """mutation { addSurveyTemplate(surveyTemplate: { 
            |    title: "COOS knee survey"
            |    sections: [{
            |        title: "how much pain are you in"
            |        inputs: [{
            |            label: "rate it 0-1"
            |            type: Number
            |            category: Body
            |            required: true
            |        }]
            |    }]
            |}) {
            |  id { value }
            | } }""".trimMargin()

        val generatedUuid = UUID.fromString("61adf575-b7ec-4554-bdca-d35d73a4a869")
        coEvery { createSurveyTemplateService.createSurveyTemplate(any()) } returns mockk(relaxed = true) {
            every { id } returns generatedUuid
        }

        graphQlTester
            .document(mutation)
            .execute()
            .errors()
            .verify()
            .path("addSurveyTemplate.id.value")
            .entity(UUID::class.java)
            .isEqualTo(generatedUuid)
    }
}