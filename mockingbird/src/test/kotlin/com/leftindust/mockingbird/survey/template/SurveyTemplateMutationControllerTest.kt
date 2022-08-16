package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateCalculationDtoToCreateSurveyTemplateCalculationConverter
import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateDtoToCreateSurveyTemplateConverter
import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateSectionDtoToCreateSurveyTemplateSectionConverter
import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateSectionInputDtoToCreateSurveyTemplateSectionInputConverter
import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateService
import com.leftindust.mockingbird.survey.template.ReadSurveyTemplateSectionInputService
import com.leftindust.mockingbird.survey.template.ReadSurveyTemplateSectionService
import com.leftindust.mockingbird.survey.template.SurveyTemplateMutationController
import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionInputQueryController
import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionQueryController
import com.leftindust.mockingbird.survey.template.SurveyTemplateToSurveyTemplateDtoConverter
import com.leftindust.mockingbird.util.SurveyTemplateMother.KoosKneeSurvey
import com.leftindust.mockingbird.util.SurveyTemplateSectionInputMother.HowMuchPainAreYouInSectionInput
import com.leftindust.mockingbird.util.SurveyTemplateSectionMother.HowMuchPainAreYouInSection
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import java.util.UUID
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.intellij.lang.annotations.Language
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
        coEvery { createSurveyTemplateService.createSurveyTemplate(any()) } returns KoosKneeSurvey.domain
        surveyTemplateMutationController.addSurveyTemplate(KoosKneeSurvey.createDto)
        coVerify(exactly = 1) { createSurveyTemplateService.createSurveyTemplate(any()) }
    }
}

@GraphQlTest(controllers = [SurveyTemplateMutationController::class, SurveyTemplateSectionQueryController::class, SurveyTemplateSectionInputQueryController::class])
internal class SurveyTemplateMutationControllerWebTest(
    @Autowired private val graphQlTester: GraphQlTester,
) {
    @MockkBean
    private lateinit var serverHttpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var createSurveyTemplateService: CreateSurveyTemplateService

    @MockkBean
    private lateinit var readSurveyTemplateSectionService: ReadSurveyTemplateSectionService

    @MockkBean
    private lateinit var readSurveyTemplateSectionInputService: ReadSurveyTemplateSectionInputService

    @Test
    internal fun `check accepts the smallest valid mutation`() {
        @Language("GraphQL")
        val mutation = """mutation { addSurveyTemplate(surveyTemplate: { 
            |    title: "COOS knee survey"
            |    sections: [{
            |        title: "how much pain are you in"
            |        inputs: [{
            |            label: "rate it 0-1"
            |            type: Number
            |            category: Body
            |            required: true
            |            calculationId: 1
            |        }]
            |        calculationId: 0        
            |    }]
            |}) {
            |  id { value }
            | } }""".trimMargin()

        coEvery { createSurveyTemplateService.createSurveyTemplate(any()) } returns KoosKneeSurvey.domain

        graphQlTester
            .document(mutation)
            .execute()
            .errors()
            .verify()
            .path("addSurveyTemplate.id.value")
            .entity(UUID::class.java)
            .isEqualTo(KoosKneeSurvey.id)
    }

    @Test
    internal fun `check creating an input and section with calculationIds returns them`() {
        coEvery { readSurveyTemplateSectionService.surveyTemplateSectionServiceBySurveySectionId(KoosKneeSurvey.graphqlId) } returns listOf(HowMuchPainAreYouInSection.domain)
        coEvery { readSurveyTemplateSectionInputService.surveyTemplateSectionInputBySurveySection(HowMuchPainAreYouInSection.graphqlId) } returns listOf(HowMuchPainAreYouInSectionInput.domain)

        val mutation = """mutation { addSurveyTemplate(surveyTemplate: { 
            |    title: "COOS knee survey"
            |    sections: [{
            |        calculationId: 0
            |        title: "how much pain are you in"
            |        inputs: [{
            |            label: "rate it 0-1"
            |            type: Number
            |            category: Body
            |            required: true
            |            calculationId: 1
            |        }]
            |    }]
            |}) {
            |  sections {
            |    calculationId
            |    inputs {
            |       calculationId            
            |    }
            |  }
            | } }""".trimMargin()

        coEvery { createSurveyTemplateService.createSurveyTemplate(any()) } returns KoosKneeSurvey.domain

        graphQlTester
            .document(mutation)
            .execute()
            .errors()
            .verify()
            .path("addSurveyTemplate.sections[0].calculationId")
            .hasValue()
            .entity(Int::class.java)
            .isEqualTo(HowMuchPainAreYouInSection.calculationId)
            .path("addSurveyTemplate.sections[0].inputs[0].calculationId")
            .hasValue()
            .entity(Int::class.java)
            .isEqualTo(HowMuchPainAreYouInSectionInput.calculationId)
    }
}