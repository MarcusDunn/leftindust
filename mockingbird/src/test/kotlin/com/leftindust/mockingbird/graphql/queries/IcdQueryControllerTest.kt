package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.icd.IcdFetcherService
import com.leftindust.mockingbird.icd.GraphQLIcdLinearizationEntity
import com.leftindust.mockingbird.icd.GraphQLIcdSearchResult
import com.leftindust.mockingbird.icd.IcdQueryController
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class IcdQueryControllerTest {
    private val client = mockk<IcdFetcherService>()

    @Test
    fun searchIcd() {
        val mockkIcdSearchResult = mockk<GraphQLIcdSearchResult> {
            every { destinationEntities } returns emptyList()
        }

        every { mockkIcdSearchResult.copy(any(), any(), any(), any(), any(), any()) } returns mockkIcdSearchResult

        coEvery { client.linearizationSearch(any(), any(), any(), any()) } returns mockkIcdSearchResult

        val icdQueryController = IcdQueryController(client)

        val result = runBlocking { icdQueryController.searchIcd("hello!", dataFetchingEnvironment = MockDataFetchingEnvironment.withVerifiedMediqToken) }

        assertEquals(mockkIcdSearchResult, result)
    }

    @Test
    fun icd() {
        val mockkIcdFoundationEntity = mockk<GraphQLIcdLinearizationEntity>()
        val icdQueryController = IcdQueryController(client)

        coEvery { client.linearizationEntity(any()) } returns mockkIcdFoundationEntity

        val result = runBlocking { icdQueryController.icd("1277781", dataFetchingEnvironment = MockDataFetchingEnvironment.withVerifiedMediqToken) }

        assertEquals(mockkIcdFoundationEntity, result)
    }
}