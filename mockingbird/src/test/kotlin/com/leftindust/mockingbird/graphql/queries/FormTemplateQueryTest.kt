package com.leftindust.mockingbird.graphql.queries

import com.expediagroup.graphql.generator.exceptions.GraphQLKotlinException
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.ReadFormDao
import com.leftindust.mockingbird.dao.entity.Form
import com.leftindust.mockingbird.graphql.types.GraphQLFormTemplate
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class FormTemplateQueryTest {
    private val formDao = mockk<ReadFormDao>()

    @Test
    fun `forms by id`() {
        val uuid  = GraphQLFormTemplate.ID(UUID.nameUUIDFromBytes("seat".toByteArray()))
        val form = mockk<Form>(relaxed = true)
        every { formDao.getByIds(listOf(uuid), any()) } returns listOf(form)

        val formTemplateQuery = FormTemplateQuery(formDao)
        val authContext = mockk<GraphQLAuthContext> {
            every { mediqAuthToken } returns mockk()
        }
        val result = runBlocking {
            formTemplateQuery.surveys(
                surveys = listOf(uuid),
                authContext = authContext
            )
        }
        assertEquals(listOf(GraphQLFormTemplate(form, authContext)), result)
    }

    @Test
    fun `forms by doctor`() {
        val form = mockk<Form>(relaxed = true)
        every { formDao.getMany(GraphQLRangeInput(0, 2), any()) } returns listOf(form)
        val formTemplateQuery = FormTemplateQuery(formDao)
        val authContext = mockk<GraphQLAuthContext> {
            every { mediqAuthToken } returns mockk()
        }

        val result = runBlocking {
            formTemplateQuery.surveys(
                range = GraphQLRangeInput(0, 2),
                authContext = authContext
            )
        }
        assertEquals(listOf(GraphQLFormTemplate(form, authContext)), result)
    }

    @Test
    fun `invalid forms argument`() {
        val formTemplateQuery = FormTemplateQuery(formDao)
        assertThrows<GraphQLKotlinException> {
            runBlocking {
                formTemplateQuery.surveys(null, null, mockk())
            }
        }
        assertThrows<GraphQLKotlinException> {
            runBlocking {
                formTemplateQuery.surveys(
                    GraphQLRangeInput(0, 2),
                    listOf(GraphQLFormTemplate.ID(UUID.nameUUIDFromBytes("seat".toByteArray()))),
                    mockk()
                )
            }
        }
    }
}