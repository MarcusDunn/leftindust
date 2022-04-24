package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.auth.NotAuthorizedException
import com.leftindust.mockingbird.dao.RecordDao
import com.leftindust.mockingbird.dao.Tables
import com.leftindust.mockingbird.dao.entity.MediqRecord
import com.leftindust.mockingbird.patient.HibernatePatientRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernateRecordRepository
import com.leftindust.mockingbird.patient.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.GraphQLPatientRecord
import com.leftindust.mockingbird.graphql.types.input.GraphQLPatientRecordInput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class RecordDaoImpl(
    @Autowired authorizer: Authorizer,
    @Autowired private val recordRepository: HibernateRecordRepository,
    @Autowired private val patientRepository: HibernatePatientRepository,
) : AbstractHibernateDao(authorizer), RecordDao {
    companion object {
        private val createRecords = Crud.CREATE to Tables.Record
        private val readRecords = Crud.READ to Tables.Record
    }

    override fun getRecordByRecordId(
        rid: GraphQLPatientRecord.ID,
        requester: MediqToken
    ): MediqRecord = if (requester can (Crud.READ to Tables.Record)) {
        recordRepository.getById(rid.id)
    } else {
        throw NotAuthorizedException(requester, Crud.READ to Tables.Record)
    }

    override fun getRecordsByPatientPid(
        pid: GraphQLPatient.ID,
        requester: MediqToken
    ): Collection<MediqRecord> = if (requester can readRecords) {
        val patient = patientRepository.getById(pid.id)
        recordRepository.getAllByPatientId(patient.id!!)
    } else {
        throw NotAuthorizedException(requester, readRecords)
    }

    override fun addRecord(
        record: GraphQLPatientRecordInput,
        requester: MediqToken
    ): MediqRecord = if (requester can createRecords) {
        val patient = patientRepository.getById(record.patient.id)
        val recordEntity = MediqRecord(record, patient)
        recordRepository.save(recordEntity)
    } else {
        throw NotAuthorizedException(requester, createRecords)
    }
}