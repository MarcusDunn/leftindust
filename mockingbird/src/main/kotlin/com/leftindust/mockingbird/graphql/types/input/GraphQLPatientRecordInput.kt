package com.leftindust.mockingbird.graphql.types.input

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.dao.entity.enums.RecordType
import com.leftindust.mockingbird.patient.GraphQLPatient

@GraphQLName("PatientRecordInput")
data class GraphQLPatientRecordInput(
    val patient: GraphQLPatient.ID,
    val jsonBlob: String,
    val recordType: RecordType
)
