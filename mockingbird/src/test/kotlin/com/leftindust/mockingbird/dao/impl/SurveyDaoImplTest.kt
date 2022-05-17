package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.survey.Survey
import com.leftindust.mockingbird.survey.HibernateAssignedFormRepository
import com.leftindust.mockingbird.survey.HibernateSurveyRepository
import com.leftindust.mockingbird.extensions.Authorization
import com.leftindust.mockingbird.survey.SurveyDto
import com.leftindust.mockingbird.survey.CreateSurveyDto
import com.leftindust.mockingbird.graphql.types.input.RangeDto
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Pageable

internal class SurveyDaoImplTest {
    private val formRepository = mockk<HibernateSurveyRepository>()
    private val assignedFormRepository = mockk<HibernateAssignedFormRepository>()

    private val authorizer = mockk<Authorizer>()

    private val uuid = UUID.nameUUIDFromBytes("seat".toByteArray())


    @Test
    fun getByIds() {
        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed

        val expected = mockk<Survey>()
        every { formRepository.findAllById(any()) } returns listOf(expected)

        val formDao = SurveyServiceImpl(formRepository, assignedFormRepository, authorizer)
        val result = formDao.getByIds(listOf(SurveyDto.SurveyDtoId(uuid)), mockk())

        assertEquals(setOf(expected), result.toSet())
    }

    @Test
    fun getByGetMany() {
        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed

        val expected = mockk<Survey>()
        every { formRepository.findAll(any<Pageable>()) } returns mockk {
            every { toList() } returns listOf(expected)
        }

        val formDao = SurveyServiceImpl(formRepository, assignedFormRepository, authorizer)
        val result = formDao.getMany(RangeDto(0, 2), mockk())

        assertEquals(setOf(expected), result.toSet())
    }

    @Test
    fun addForm() {
        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed

        val expected = mockk<Survey>()

        val mockkGqlForm = mockk<CreateSurveyDto>(relaxed = true)

        every { formRepository.save(any()) } returns expected

        val formDao = SurveyServiceImpl(formRepository, assignedFormRepository, authorizer)

        val result = formDao.addForm(mockkGqlForm, mockk())

        assertEquals(expected, result)
    }


    @Test
    fun deleteForm() {
        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed

        val expected = mockk<Survey>()
        every { formRepository.getById(any()) } returns expected
        every { formRepository.delete(any()) } just runs

        val formDao = SurveyServiceImpl(formRepository, assignedFormRepository, authorizer)

        val result = formDao.deleteForm(mockk { every { id } returns mockk() }, mockk())

        assertEquals(expected, result)
    }
}