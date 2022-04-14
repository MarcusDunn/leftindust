package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.dao.RecordDao
import com.leftindust.mockingbird.dao.entity.MediqRecord
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.GraphQLRecord
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class RecordQueryTest {
    private val recordDao = mockk<RecordDao>()

    @Test
    fun getRecord() {
        val recordID = UUID.randomUUID()

        val mockkRecord = mockk<MediqRecord>(relaxed = true) {
            every { id } returns recordID
        }
        every { recordDao.getRecordByRecordId(GraphQLRecord.ID(recordID), any()) } returns mockkRecord

        val recordQuery = RecordQuery(recordDao)

        val result = runBlocking {
            recordQuery.records(
                rids = listOf(GraphQLRecord.ID(recordID)), dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }

        assertEquals(GraphQLRecord(mockkRecord), result.first())
    }

    @Test
    fun getRecords() {
        val recordID = UUID.randomUUID()
        val patientID = UUID.randomUUID()

        val mockkRecord = mockk<MediqRecord>(relaxed = true) {
            every { id } returns recordID
        }
        every { recordDao.getRecordsByPatientPid(GraphQLPatient.ID(patientID), any()) } returns listOf(mockkRecord)

        val recordQuery = RecordQuery(recordDao)

        val result =
            runBlocking { recordQuery.records(pid = GraphQLPatient.ID(patientID), dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken) }

        assertEquals(listOf(GraphQLRecord(mockkRecord)), result)
    }
}