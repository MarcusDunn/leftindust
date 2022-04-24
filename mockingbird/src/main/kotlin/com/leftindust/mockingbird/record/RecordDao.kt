package com.leftindust.mockingbird.record

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.patient.GraphQLPatient

interface RecordDao {
    @Blocking
    fun getRecordByRecordId(rid: GraphQLPatientRecord.ID, requester: MediqToken): MediqRecord

    @Blocking
    fun getRecordsByPatientPid(pid: GraphQLPatient.ID, requester: MediqToken): Collection<MediqRecord>

    @Blocking
    fun addRecord(record: GraphQLPatientRecordInput, requester: MediqToken): MediqRecord
}
