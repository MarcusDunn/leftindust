package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.util.SurveyTemplateMother
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class SurveyTemplateQueryControllerUnitTest {
    @MockK
    private lateinit var readSurveyTemplateService: ReadSurveyTemplateService

    private val surveyTemplateToSurveyTemplateDtoConverter = SurveyTemplateToSurveyTemplateDtoConverter()

    @Test
    internal fun `check can get a surveyTemplateDto by id`() = runTest {
        val surveyTemplateQueryController =
            SurveyTemplateQueryController(readSurveyTemplateService, surveyTemplateToSurveyTemplateDtoConverter)
        val surveyTemplateEntity = SurveyTemplateMother.`koos knee survey template entity persisted`
        val surveyTemplateDtoId = SurveyTemplateDto.SurveyTemplateDtoId(
            surveyTemplateEntity.id!!
        )
        coEvery { readSurveyTemplateService.getSurveyTemplateBySurveyId(surveyTemplateDtoId) } returns SurveyTemplateMother.`koos knee survey template`

        val result = surveyTemplateQueryController.surveyTemplateBySurveyTemplateId(surveyTemplateDtoId)

        assertThat(result, equalTo(SurveyTemplateMother.`koos knee survey template dto`))
    }

    @Test
    internal fun `check getSurveyTemplateBySurveyId returns null if no such SurveyTemplateId`() = runTest {
        val surveyTemplateQueryController = SurveyTemplateQueryController(readSurveyTemplateService, surveyTemplateToSurveyTemplateDtoConverter)
        val nonExistentId = SurveyTemplateDto.SurveyTemplateDtoId(UUID.fromString("75868a7e-3e95-4018-9564-d28660211b21"))
        coEvery { readSurveyTemplateService.getSurveyTemplateBySurveyId(nonExistentId) } returns null

        val result = surveyTemplateQueryController.surveyTemplateBySurveyTemplateId(nonExistentId)

        assertThat(result, nullValue())
    }

    @Test
    internal fun `check can get surveyTemplateDto by range`() = runTest {
        val surveyTemplateQueryController = SurveyTemplateQueryController(readSurveyTemplateService, surveyTemplateToSurveyTemplateDtoConverter)
        val rangeDto = RangeDto(0, 10)
        coEvery { readSurveyTemplateService.getSurveyTemplateByRange(rangeDto) } returns List(rangeDto.size) { SurveyTemplateMother.`koos knee survey template` }

        val result = surveyTemplateQueryController.surveyTemplateByRange(rangeDto)

        assertThat(result, hasSize(equalTo(rangeDto.size)))
    }
}

@GraphQlTest(controllers = [SurveyTemplateQueryController::class, SurveyTemplateSectionsQueryController::class])
internal class SurveyTemplateQueryControllerWebTest(
    @Autowired private val graphqlTester: GraphQlTester
) {
    @MockkBean
    private lateinit var serverHttpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readSurveyTemplateService: ReadSurveyTemplateService

    @MockkBean
    private lateinit var readSurveyTemplateSectionService: ReadSurveyTemplateSectionService

    @Test
    internal fun `check can query by id`() {
        val surveyTemplateEntity = SurveyTemplateMother.`koos knee survey template entity persisted`
        val surveyTemplateDtoId = SurveyTemplateDto.SurveyTemplateDtoId(
            surveyTemplateEntity.id!!
        )
        coEvery { readSurveyTemplateService.getSurveyTemplateBySurveyId(surveyTemplateDtoId) } returns SurveyTemplateMother.`koos knee survey template`
        coEvery { readSurveyTemplateSectionService.getSurveyTemplateSectionsBySurveyTemplateId(surveyTemplateDtoId) } returns SurveyTemplateMother.`koos knee survey template sections`

        val query = """
            query { 
                surveyTemplateBySurveyTemplateId(surveyTemplateId: {value: "${surveyTemplateEntity.id}"}) {
                    id { value }
                    sections {
                        title                    
                    }
                } 
            }
            """

        graphqlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("surveyTemplateBySurveyTemplateId.id.value")
            .entity(UUID::class.java)
            .isEqualTo(surveyTemplateEntity.id!!)
            .path("surveyTemplateBySurveyTemplateId.sections[*].title")
            .entity(object : ParameterizedTypeReference<List<String>>() {})
            .isEqualTo(surveyTemplateEntity.sections.map { it.title })
    }

    @Test
    internal fun `check can query by range`() {
        val surveyTemplateEntity = SurveyTemplateMother.`koos knee survey template entity persisted`
        coEvery { readSurveyTemplateService.getSurveyTemplateByRange(RangeDto(0, 10)) } returns List(10) { SurveyTemplateMother.`koos knee survey template` }

        val query = """
            query { 
                surveyTemplateByRange(range: {from: 0, to: 10}) {
                    id { value }
                } 
            }
            """

        graphqlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("surveyTemplateByRange[*].id.value")
            .entity(object : ParameterizedTypeReference<List<UUID>>() {})
            .isEqualTo(List(10) { surveyTemplateEntity.id!! })
    }
}
