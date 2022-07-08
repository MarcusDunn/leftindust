package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.util.SurveyTemplateMother
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.security.web.server.SecurityWebFilterChain
import java.util.Optional
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class ReadSurveyTemplateServiceImplUnitTest {
    @MockK
    private lateinit var surveyTemplateEntityRepository: SurveyTemplateEntityRepository
    private val surveyTemplateEntityToSurveyTemplateConverter = SurveyTemplateEntityToSurveyTemplateConverter()

    @Test
    internal fun `check returns not null when entity with id found in repository`() = runTest {
        val readSurveyTemplateServiceImpl = ReadSurveyTemplateServiceImpl(surveyTemplateEntityRepository, surveyTemplateEntityToSurveyTemplateConverter)

        val surveyTemplateEntity = SurveyTemplateMother.`koos knee survey template entity persisted`
        every { surveyTemplateEntityRepository.findById(surveyTemplateEntity.id!!) } returns Optional.of(surveyTemplateEntity)

        val result = readSurveyTemplateServiceImpl.getSurveyTemplateBySurveyId(SurveyTemplateDto.SurveyTemplateDtoId(surveyTemplateEntity.id!!))

        assertThat(result, notNullValue())
    }

    @Test
    internal fun `check returns null when entity with id is not found in repository`() = runTest {
        val readSurveyTemplateServiceImpl = ReadSurveyTemplateServiceImpl(surveyTemplateEntityRepository, surveyTemplateEntityToSurveyTemplateConverter)

        val nonExistentId = UUID.fromString("0bb2774c-1216-4834-8624-cf8d2adf4081")

        every { surveyTemplateEntityRepository.findById(nonExistentId) } returns Optional.empty()

        val result = readSurveyTemplateServiceImpl.getSurveyTemplateBySurveyId(SurveyTemplateDto.SurveyTemplateDtoId(nonExistentId))

        assertThat(result, nullValue())
    }

    @Test
    internal fun `check can get by range`() = runTest {
        val readSurveyTemplateServiceImpl = ReadSurveyTemplateServiceImpl(surveyTemplateEntityRepository, surveyTemplateEntityToSurveyTemplateConverter)

        every { surveyTemplateEntityRepository.findAll(PageRequest.of(0, 10)) } returns PageImpl(List(10) { SurveyTemplateMother.`koos knee survey template entity persisted` })

        val result = readSurveyTemplateServiceImpl.getSurveyTemplateByRange(RangeDto(0, 10))

        assertThat(result, hasSize(equalTo(10)))
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@DataJpaTest
internal class ReadSurveyTemplateServiceImplJpaTest(
    @Autowired private val testEntityManager: TestEntityManager,
    @Autowired private val surveyTemplateEntityRepository: SurveyTemplateEntityRepository,
) {
    @MockkBean
    private lateinit var serverHttpSecurity: SecurityWebFilterChain

    private val surveyTemplateEntityToSurveyTemplateConverter = SurveyTemplateEntityToSurveyTemplateConverter()

    @Test
    internal fun `check getting by range does not return more than range size`() = runTest {
        val readSurveyTemplateServiceImpl = ReadSurveyTemplateServiceImpl(surveyTemplateEntityRepository, surveyTemplateEntityToSurveyTemplateConverter)
        repeat(5) {
            testEntityManager.persist(SurveyTemplateMother.`koos knee survey template entity unpersisted`.apply { title = it.toString() })
        }

        val result = readSurveyTemplateServiceImpl.getSurveyTemplateByRange(RangeDto(1, 3))

        assertThat(result, hasSize(equalTo(2)))
    }

    @Test
    internal fun `check getting by range does not return same entity twice for different ranges`() = runTest {
        val readSurveyTemplateServiceImpl = ReadSurveyTemplateServiceImpl(surveyTemplateEntityRepository, surveyTemplateEntityToSurveyTemplateConverter)
        repeat(5) {
            testEntityManager.persist(SurveyTemplateMother.`koos knee survey template entity unpersisted`.apply { title = it.toString() })
        }

        val result1 = readSurveyTemplateServiceImpl.getSurveyTemplateByRange(RangeDto(0, 2))
        val result2 = readSurveyTemplateServiceImpl.getSurveyTemplateByRange(RangeDto(2, 5))

        assertThat(result1.toSet() + result2, hasSize(equalTo(5)))
    }
}