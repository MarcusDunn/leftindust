package com.leftindust.mockingbird.record

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.patient.PatientEntity
import java.sql.Timestamp
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToOne

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
