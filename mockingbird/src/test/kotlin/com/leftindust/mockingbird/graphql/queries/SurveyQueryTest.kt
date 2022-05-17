package com.leftindust.mockingbird.graphql.queries

import com.expediagroup.graphql.generator.exceptions.GraphQLKotlinException
import com.leftindust.mockingbird.survey.ReadSurveyService
import com.leftindust.mockingbird.survey.Survey
import com.leftindust.mockingbird.survey.SurveyQueryController
import com.leftindust.mockingbird.survey.SurveyDto
import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

internal class SurveyQueryTest {
    private val formDao = mockk<ReadSurveyService>()

    @Test
    fun `forms by id`() {
        val uuid = SurveyDto.SurveyDtoId(UUID.nameUUIDFromBytes("seat".toByteArray()))
        val survey = mockk<Survey>(relaxed = true)
        every { formDao.getBySurveyIds(listOf(uuid), any()) } returns listOf(survey)

        val surveyQueryController = SurveyQueryController(formDao)

        val result = runBlocking {
            surveyQueryController.surveys(
                surveys = listOf(uuid),
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }
        assertEquals(listOf(SurveyDto(survey)), result)
    }

    @Test
    fun `forms by doctor`() {
        val survey = mockk<Survey>(relaxed = true)
        every { formDao.getMany(RangeDto(0, 2), any()) } returns listOf(survey)
        val surveyQueryController = SurveyQueryController(formDao)

        val result = runBlocking {
            surveyQueryController.surveys(
                range = RangeDto(0, 2),
                dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }
        assertEquals(listOf(SurveyDto(survey)), result)
    }

    @Test
    fun `invalid forms argument`() {
        val surveyQueryController = SurveyQueryController(formDao)
        assertThrows<GraphQLKotlinException> {
            runBlocking {
                surveyQueryController.surveys(null, null, mockk())
            }
        }
        assertThrows<GraphQLKotlinException> {
            runBlocking {
                surveyQueryController.surveys(
                    RangeDto(0, 2),
                    listOf(SurveyDto.SurveyDtoId(UUID.nameUUIDFromBytes("seat".toByteArray()))),
                    mockk()
                )
            }
        }
    }
}