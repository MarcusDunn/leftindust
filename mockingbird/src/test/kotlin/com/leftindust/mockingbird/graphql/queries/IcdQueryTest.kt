package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.icd.IcdFetcher
import com.leftindust.mockingbird.icd.GraphQLIcdLinearizationEntity
import com.leftindust.mockingbird.icd.GraphQLIcdSearchResult
import com.leftindust.mockingbird.icd.IcdQuery
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class IcdQueryTest {
    private val client = mockk<IcdFetcher>()

    @Test
    fun searchIcd() {
        val mockkIcdSearchResult = mockk<GraphQLIcdSearchResult> {
            every { destinationEntities } returns emptyList()
        }

        every { mockkIcdSearchResult.copy(any(), any(), any(), any(), any(), any()) } returns mockkIcdSearchResult

        coEvery { client.linearizationSearch(any(), any(), any(), any()) } returns mockkIcdSearchResult

        val icdQuery = IcdQuery(client)

        val result = runBlocking { icdQuery.searchIcd("hello!", dataFetchingEnvironment = MockDataFetchingEnvironment.withVerifiedMediqToken) }

        assertEquals(mockkIcdSearchResult, result)
    }

    @Test
    fun icd() {
        val mockkIcdFoundationEntity = mockk<GraphQLIcdLinearizationEntity>()
        val icdQuery = IcdQuery(client)

        coEvery { client.linearizationEntity(any()) } returns mockkIcdFoundationEntity

        val result = runBlocking { icdQuery.icd("1277781", dataFetchingEnvironment = MockDataFetchingEnvironment.withVerifiedMediqToken) }

        assertEquals(mockkIcdFoundationEntity, result)
    }
}