package com.leftindust.mockingbird.record

import com.leftindust.mockingbird.patient.PatientDto

data class CreateRecordDto(
    val patient: PatientDto.PatientDtoId,
    val jsonBlob: String,
    val recordType: RecordType,
)

interface CreateRecord {
    val patient: PatientDto.PatientDtoId
    val jsonBlob: String
    val recordType: RecordType
}