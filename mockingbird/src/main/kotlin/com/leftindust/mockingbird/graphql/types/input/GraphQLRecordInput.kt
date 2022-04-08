package com.leftindust.mockingbird.graphql.types.input

import com.leftindust.mockingbird.dao.entity.enums.RecordType
import com.leftindust.mockingbird.graphql.types.GraphQLPatient

data class GraphQLRecordInput(val patient: GraphQLPatient.ID, val jsonBlob: String, val recordType: RecordType)
