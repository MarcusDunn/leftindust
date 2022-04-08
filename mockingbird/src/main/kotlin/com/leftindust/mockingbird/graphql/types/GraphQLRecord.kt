package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.entity.MediqRecord
import com.leftindust.mockingbird.dao.entity.Patient
import com.leftindust.mockingbird.dao.entity.enums.RecordType
import java.util.UUID

@GraphQLName("Record")
data class GraphQLRecord(
    val rid: ID,
    val creationDate: GraphQLUtcTime,
    val type: RecordType,
    val jsonBlob: String,
    private val patient: Patient,
    private val authContext: GraphQLAuthContext,
) {
    @GraphQLName("RecordId")
    data class ID(val id: UUID)

    constructor(record: MediqRecord, graphQLAuthContext: GraphQLAuthContext) : this(
        rid = ID(record.id!!),
        creationDate = GraphQLUtcTime(record.creationDate),
        jsonBlob = record.jsonBlob,
        type = record.type,
        patient = record.patient,
        authContext = graphQLAuthContext,
    )

    fun patient(): GraphQLPatient = GraphQLPatient(patient, authContext)
}
