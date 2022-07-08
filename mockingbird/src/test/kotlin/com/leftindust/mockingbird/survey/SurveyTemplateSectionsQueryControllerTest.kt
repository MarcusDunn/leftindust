package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateService
import com.leftindust.mockingbird.survey.template.ReadSurveyTemplateService
import com.leftindust.mockingbird.survey.template.SurveyTemplateDto
import com.leftindust.mockingbird.survey.template.SurveyTemplateMutationController
import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionsQueryController
import com.leftindust.mockingbird.util.SurveyTemplateMother
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain
import java.util.UUID

@GraphQlTest(controllers = [SurveyTemplateMutationController::class, SurveyTemplateSectionsQueryController::class])
internal class SurveyTemplateSectionsQueryControllerTest(
    @Autowired private val graphQlTester: GraphQlTester,
) {
    @MockkBean
    private lateinit var serverHttpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var createSurveyTemplateService: CreateSurveyTemplateService

    @MockkBean
    private lateinit var readSurveyTemplateService: ReadSurveyTemplateService

    @Test
    internal fun `check accepts the smallest valid mutation`() {
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
            |  sections { id { value } }
            | } }""".trimMargin()

        val surveyTemplate = SurveyTemplateMother.`koos knee survey template`
        coEvery { createSurveyTemplateService.createSurveyTemplate(any()) } returns surveyTemplate
        coEvery { readSurveyTemplateService.getSurveySections(SurveyTemplateDto.SurveyTemplateDtoId(surveyTemplate.id)) } returns SurveyTemplateMother.`koos knee survey template sections`

        graphQlTester
            .document(mutation)
            .execute()
            .errors()
            .verify()
            .path("addSurveyTemplate.sections[*].id.value")
            .entity(object : ParameterizedTypeReference<List<UUID>>() {})
            .isEqualTo(SurveyTemplateMother.`koos knee survey template entity persisted`.sections.map { it.id})
    }
}