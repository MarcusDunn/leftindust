package com.leftindust.mockingbird.record

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.patient.PatientEntity
import java.sql.Timestamp
import java.time.Instant
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.ManyToOne

@Entity
class MediqRecord(
    @ManyToOne
    var patient: PatientEntity,
    val creationDate: Timestamp,
    @Enumerated(EnumType.STRING)
    val type: RecordType,
    @Column(length = 10_000, nullable = false)
    val jsonBlob: String,
) : AbstractJpaPersistable() {
    constructor(record: CreateRecordDto, patient: PatientEntity) : this(
        patient = patient,
        creationDate = Timestamp.from(Instant.now()),
        type = record.recordType,
        jsonBlob = record.jsonBlob,
    )
}
