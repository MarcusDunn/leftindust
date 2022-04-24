package com.leftindust.mockingbird.external.icd.impl

import com.leftindust.mockingbird.icd.IcdFetcher
import com.leftindust.mockingbird.icd.GraphQLFoundationIcdCode
import com.leftindust.mockingbird.util.integration.NoAuthIntegrationTest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class IcdFetcherIntegrationTest(
    @Autowired private val icdFetcher: IcdFetcher
) : NoAuthIntegrationTest() {

    @Test
    fun `check linearization success`(): Unit = runBlocking {
        val result =
            icdFetcher.linearization(GraphQLFoundationIcdCode("http://id.who.int/icd/release/11/2020-09/mms/236924369/unspecified"))
        assertEquals("Human immunodeficiency virus disease associated with tuberculosis", result.title.value)
    }

    @Test
    fun `check linearizationSearch success`(): Unit = runBlocking {
        val result = icdFetcher.linearizationSearch("aids")
        assertEquals(false, result.error)
        assertTrue(result.destinationEntities?.size!! >= 10)
    }

    @Test
    fun `check getDetails success`(): Unit = runBlocking {
        val result =
            icdFetcher.getDetails(GraphQLFoundationIcdCode("http://id.who.int/icd/release/11/2020-09/mms/236924369/unspecified"))
        assertEquals("Human immunodeficiency virus disease associated with tuberculosis", result.title?.value)
    }

    @Test
    fun `check search success`(): Unit = runBlocking {
        val result = icdFetcher.search("aids")
        assertEquals(false, result.error)
        assertTrue(result.destinationEntities?.size!! >= 10)
    }

    @Test
    fun `check linearizationEntity success`(): Unit = runBlocking {
        val result =
            icdFetcher.linearizationEntity(GraphQLFoundationIcdCode("http://id.who.int/icd/release/11/2020-09/mms/236924369/unspecified"))
        assertEquals("Human immunodeficiency virus disease associated with tuberculosis", result.title)
    }
}