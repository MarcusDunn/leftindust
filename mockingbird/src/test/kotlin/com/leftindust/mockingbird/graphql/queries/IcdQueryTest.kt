package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.external.icd.IcdFetcher
import com.leftindust.mockingbird.graphql.types.icd.GraphQLIcdLinearizationEntity
import com.leftindust.mockingbird.graphql.types.icd.GraphQLIcdSearchResult
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class IcdQueryTest {
    private val client = mockk<IcdFetcher>()
    private val authContext = mockk<GraphQLAuthContext>()

    @Test
    fun searchIcd() {
        val mockkIcdSearchResult = mockk<GraphQLIcdSearchResult> {
            every { destinationEntities } returns emptyList()
        }

        every { mockkIcdSearchResult.copy(any(), any(), any(), any(), any(), any()) } returns mockkIcdSearchResult

        coEvery { client.linearizationSearch(any(), any(), any(), any()) } returns mockkIcdSearchResult

        every { authContext.mediqAuthToken } returns mockk {
            every { isVerified() } returns true
        }

        val icdQuery = IcdQuery(client)

        val result = runBlocking { icdQuery.searchIcd("hello!", authContext = authContext) }

        assertEquals(mockkIcdSearchResult, result)
    }

    @Test
    fun icd() {
        val mockkIcdFoundationEntity = mockk<GraphQLIcdLinearizationEntity>()
        val icdQuery = IcdQuery(client)

        coEvery { client.linearizationEntity(any()) } returns mockkIcdFoundationEntity

        every { authContext.mediqAuthToken } returns mockk {
            every { isVerified() } returns true
        }

        val result = runBlocking { icdQuery.icd("1277781", authContext = authContext) }

        assertEquals(mockkIcdFoundationEntity, result)
    }
}