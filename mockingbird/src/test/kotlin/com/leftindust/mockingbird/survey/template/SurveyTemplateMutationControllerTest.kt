package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateCalculationDtoToCreateSurveyTemplateCalculationConverter
import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateDtoToCreateSurveyTemplateConverter
import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateSectionDtoToCreateSurveyTemplateSectionConverter
import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateSectionInputDtoToCreateSurveyTemplateSectionInputConverter
import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateService
import com.leftindust.mockingbird.survey.template.SurveyTemplateMutationController
import com.leftindust.mockingbird.survey.template.SurveyTemplateToSurveyTemplateDtoConverter
import com.leftindust.mockingbird.util.SurveyTemplateMother
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class SurveyTemplateMutationControllerUnitTest {
    @MockK
    private lateinit var createSurveyTemplateService: CreateSurveyTemplateService

    private val surveyTemplateMutationController by lazy {
        SurveyTemplateMutationController(
            createSurveyTemplateService,
            SurveyTemplateToSurveyTemplateDtoConverter(),
            CreateSurveyTemplateDtoToCreateSurveyTemplateConverter(
                CreateSurveyTemplateSectionDtoToCreateSurveyTemplateSectionConverter(CreateSurveyTemplateSectionInputDtoToCreateSurveyTemplateSectionInputConverter()),
                CreateSurveyTemplateCalculationDtoToCreateSurveyTemplateCalculationConverter()
            )
        )
    }

    @Test
    internal fun `check calls the creation service`() = runTest {
        coEvery { createSurveyTemplateService.createSurveyTemplate(any()) } returns SurveyTemplateMother.KoosKneeSurvey.domain
        surveyTemplateMutationController.addSurveyTemplate(SurveyTemplateMother.KoosKneeSurvey.createDto)
        coVerify(exactly = 1) { createSurveyTemplateService.createSurveyTemplate(any()) }
    }
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