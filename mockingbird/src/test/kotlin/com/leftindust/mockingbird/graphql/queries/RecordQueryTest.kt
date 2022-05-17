package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.record.MediqRecord
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.record.RecordDto
import com.leftindust.mockingbird.record.RecordQuery
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
        every { recordDao.getRecordByRecordId(RecordDto.RecordDtoId(recordID), any()) } returns mockkRecord

        val recordQuery = RecordQuery(recordDao)

        val result = runBlocking {
            recordQuery.records(
                rids = listOf(RecordDto.RecordDtoId(recordID)), dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken
            )
        }

        assertEquals(RecordDto(mockkRecord), result.first())
    }

    @Test
    fun getRecords() {
        val recordID = UUID.randomUUID()
        val patientID = UUID.randomUUID()

        val mockkRecord = mockk<MediqRecord>(relaxed = true) {
            every { id } returns recordID
        }
        every { recordDao.getRecordsByPatientPid(PatientDto.PatientDtoId(patientID), any()) } returns listOf(mockkRecord)

        val recordQuery = RecordQuery(recordDao)

        val result =
            runBlocking { recordQuery.records(pid = PatientDto.PatientDtoId(patientID), dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken) }

        assertEquals(listOf(RecordDto(mockkRecord)), result)
    }
}