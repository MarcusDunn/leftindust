package com.leftindust.mockingbird.graphql.mutations

import com.leftindust.mockingbird.record.RecordDto
import com.leftindust.mockingbird.record.RecordMutationController
import com.leftindust.mockingbird.util.EntityStore
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class RecordMutationControllerTest {
    private val recordDao = mockk<RecordDao>()

    @Test
    fun addRecord() {

        val expected =
            EntityStore.record("RecordMutationTest.addRecord", EntityStore.patient("RecordMutationTest.addRecord"))
                .apply { id = UUID.nameUUIDFromBytes("kueryhvfor".toByteArray()) }

        every { recordDao.addRecord(any(), any()) } returns expected
        val result = runBlocking {
            RecordMutationController(recordDao).addRecord(mockk(), MockDataFetchingEnvironment.withDummyMediqToken)
        }

        assertEquals(RecordDto(expected), result)
    }
}