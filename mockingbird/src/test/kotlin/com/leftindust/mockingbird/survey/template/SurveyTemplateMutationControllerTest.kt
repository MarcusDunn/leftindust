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
        @Language("graphql")
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
            |})  {
            |  id { value } 
            |  sections {
            |    calculationId
            |    inputs {
            |       calculationId            
            |    }
            |  }
            | 
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

    @Test
    internal fun `check creating an input with multiple sections with calculationIds returns them`() {
        coEvery { readSurveyTemplateSectionService.surveyTemplateSectionServiceBySurveySectionId(KoosKneeSurvey.graphqlId) } returns listOf(HowMuchPainAreYouInSection.domain)
        coEvery { readSurveyTemplateSectionInputService.surveyTemplateSectionInputBySurveySection(HowMuchPainAreYouInSection.graphqlId) } returns listOf(HowMuchPainAreYouInSectionInput.domain)

        @Language("graphql")
        val mutation = """mutation { addSurveyTemplate(surveyTemplate: {
        |   title: "Test"
        |   subtitle: "Survey Template Test"
        |   sections: [{
        |       title: "Information"
        |       calculationId: 0,
        |       inputs: [{
        |           calculationId: 1
        |           category: Body
        |           label: "Whats you name?"
        |           options: [
        |           "" 
        |           ],
        |           placeholder: "Eg. John Doe",
        |           required: false,
        |           type: Text,
        |           uploadAccept: All,
        |           uploadMultiple: true,
        |           },
        |           {
        |            calculationId: 5,
        |             category: Body,
        |             label: "How old are you?",
        |             options:[
        |                ""
        |             ],
        |             placeholder: "Eg. 20",
        |             required: false,
        |             type: Number,
        |             uploadAccept: All,
        |             uploadMultiple: true
        |          }
        |       ]
        |    },
        |    {
        |       title: "Occupation"
        |       calculationId: 3
        |       inputs:[
        |          {
        |             calculationId: 4
        |             category: Body
        |             label: "What do you do?"
        |             options:[
        |                ""
        |             ],
        |             placeholder: "Eg. Engineer",
        |             required: false,
        |             type: Text,
        |             uploadAccept: All,
        |             uploadMultiple: true
        |          }
        |       ]
        |    }
        | ]
        | calculations:[
        |     {
        |       label: "Name",
        |       calculation:"{\"nodes\":{\"0\":{\"type\":\"output\",\"x\":846,\"y\":140,\"store\":{\"index\":0},\"inputs\":{\"Value\":{\"type\":\"text\",\"connection\":{\"connectedNodeId\":\"1\",\"connectedSocketId\":\"Value\"}}},\"outputs\":{}},\"1\":{\"type\":\"Input\",\"x\":41,\"y\":456.5,\"store\":{\"id\":1},\"outputs\":{\"Value\":{\"type\":\"text\",\"value\":\"John\"}},\"inputs\":{}}},\"position\":{\"originX\":0,\"originY\":0,\"translateX\":0,\"translateY\":0,\"scale\":1"
        |       inputType: Text,
        |       showOnComplete: true
        |     }
        |   ]
        | })
        |  {
        |    id { value }
        |    sections {
        |        calculationId
        |            inputs {
        |                calculationId 
        |            }
        |    }
        |  }
        | }""".trimMargin()

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