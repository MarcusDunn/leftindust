package com.leftindust.mockingbird.dao

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.dao.entity.MediqRecord
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.GraphQLRecord
import com.leftindust.mockingbird.graphql.types.input.GraphQLRecordInput

interface RecordDao {
    @Blocking
    fun getRecordByRecordId(rid: GraphQLRecord.ID, requester: MediqToken): MediqRecord

    @Blocking
    fun getRecordsByPatientPid(pid: GraphQLPatient.ID, requester: MediqToken): Collection<MediqRecord>

    @Blocking
    fun addRecord(record: GraphQLRecordInput, requester: MediqToken): MediqRecord
}
