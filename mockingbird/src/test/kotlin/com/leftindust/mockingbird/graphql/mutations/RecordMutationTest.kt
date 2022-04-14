package com.leftindust.mockingbird.graphql.mutations

import com.leftindust.mockingbird.dao.RecordDao
import com.leftindust.mockingbird.graphql.types.GraphQLRecord
import com.leftindust.mockingbird.util.EntityStore
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class RecordMutationTest {
    private val recordDao = mockk<RecordDao>()

    @Test
    fun addRecord() {

        val expected =
            EntityStore.record("RecordMutationTest.addRecord", EntityStore.patient("RecordMutationTest.addRecord"))
                .apply { id = UUID.nameUUIDFromBytes("kueryhvfor".toByteArray()) }

        every { recordDao.addRecord(any(), any()) } returns expected
        val result = runBlocking {
            RecordMutation(recordDao).addRecord(mockk(), MockDataFetchingEnvironment.withDummyMediqToken)
        }

        assertEquals(GraphQLRecord(expected), result)
    }
}