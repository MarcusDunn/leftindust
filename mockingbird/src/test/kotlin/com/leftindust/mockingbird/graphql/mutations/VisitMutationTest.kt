package com.leftindust.mockingbird.graphql.mutations

import com.leftindust.mockingbird.visit.VisitDao
import com.leftindust.mockingbird.visit.Visit
import com.leftindust.mockingbird.visit.VisitDto
import com.leftindust.mockingbird.visit.GraphQLVisitInput
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import com.leftindust.mockingbird.visit.VisitMutation
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class VisitMutationTest {
    private val visitDao = mockk<VisitDao>()

    @Test
    fun addVisit() {
        val visitId = UUID.randomUUID()

        val mockkVisit = mockk<Visit>(relaxed = true) {
            every { id } returns visitId
        }

        every { visitDao.addVisit(any(), any()) } returns mockkVisit

        val visitMutation = VisitMutation(visitDao)

        val visitMockk = mockk<GraphQLVisitInput>()

        val result = runBlocking { visitMutation.addVisit(visitMockk, MockDataFetchingEnvironment.withDummyMediqToken) }

        assertEquals(VisitDto(mockkVisit), result)
    }
}