package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.dao.entity.MediqRecord
import com.leftindust.mockingbird.dao.entity.Patient
import com.leftindust.mockingbird.dao.impl.repository.HibernatePatientRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernateRecordRepository
import com.leftindust.mockingbird.extensions.Authorization
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.GraphQLRecord
import com.leftindust.mockingbird.util.EntityStore
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RecordDaoImplTest {
    private val authorizer = mockk<Authorizer>()
    private val recordRepository = mockk<HibernateRecordRepository>()
    private val patientRepository = mockk<HibernatePatientRepository>()


    @Test
    fun getRecordByRecordId() {
        val mockkRecord = mockk<MediqRecord>()

        val recordId = UUID.randomUUID()

        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed
        every { recordRepository.getById(recordId) } returns mockkRecord

        val recordDaoImpl = RecordDaoImpl(authorizer, recordRepository, patientRepository)

        val result = recordDaoImpl.getRecordByRecordId(GraphQLRecord.ID(recordId), mockk())

        assertEquals(mockkRecord, result)
    }

    @Test
    fun getRecordsByPatientPid() {
        val mockkRecord = mockk<MediqRecord>()
        val patientID = UUID.randomUUID()

        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed

        val mockkPatient = mockk<Patient> {
            every { id } returns patientID
        }

        every { patientRepository.getById(patientID) } returns mockkPatient
        every { recordRepository.getAllByPatientId(patientID) } returns listOf(mockkRecord)

        val recordDaoImpl = RecordDaoImpl(authorizer, recordRepository, patientRepository)

        val result = recordDaoImpl.getRecordsByPatientPid(GraphQLPatient.ID(patientID), mockk())

        assertEquals(listOf(mockkRecord), result)
    }

    @Test
    fun addRecord() {
        val recordDaoImpl = RecordDaoImpl(authorizer, recordRepository, patientRepository)

        val record = EntityStore.graphQLRecordInput("RecordDaoImplTest.addRecord")

        val expected = MediqRecord(record, mockk())

        every { recordRepository.save(any()) } returns expected

        every { patientRepository.getById(record.patient.id) } returns mockk()

        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed

        val result = recordDaoImpl.addRecord(record, mockk())

        assertEquals(expected, result)
    }
}