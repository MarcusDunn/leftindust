package com.leftindust.mockingbird.graphql.mutations

import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.RecordDao
import com.leftindust.mockingbird.graphql.types.GraphQLRecord
import com.leftindust.mockingbird.util.EntityStore
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RecordMutationTest {
    private val recordDao = mockk<RecordDao>()

    @Test
    fun addRecord() {
        val graphQLAuthContext = mockk<GraphQLAuthContext> {
            every { mediqAuthToken } returns mockk()
        }
        val expected =
            EntityStore.record("RecordMutationTest.addRecord", EntityStore.patient("RecordMutationTest.addRecord"))
                .apply { id = UUID.nameUUIDFromBytes("kueryhvfor".toByteArray()) }

        every { recordDao.addRecord(any(), graphQLAuthContext.mediqAuthToken) } returns expected
        val result = runBlocking { RecordMutation(recordDao).addRecord(mockk(), graphQLAuthContext) }

        assertEquals(GraphQLRecord(expected, graphQLAuthContext), result)
    }
}