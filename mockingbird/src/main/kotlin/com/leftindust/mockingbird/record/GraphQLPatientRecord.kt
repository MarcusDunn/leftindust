package com.leftindust.mockingbird.record

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.graphql.types.GraphQLUtcTime
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.patient.GraphQLPatient
import java.util.*

@GraphQLName("PatientRecord")
data class GraphQLPatientRecord(
    val rid: ID,
    val creationDate: GraphQLUtcTime,
    val type: RecordType,
    val jsonBlob: String,
    private val patient: Patient,
) {
    @GraphQLName("PatientRecordId")
    data class ID(val id: UUID)

    constructor(record: MediqRecord) : this(
        rid = ID(record.id!!),
        creationDate = GraphQLUtcTime(record.creationDate),
        jsonBlob = record.jsonBlob,
        type = record.type,
        patient = record.patient, // todo get patient lazily perhaps?
    )

    fun patient(): GraphQLPatient = GraphQLPatient(patient)
}
