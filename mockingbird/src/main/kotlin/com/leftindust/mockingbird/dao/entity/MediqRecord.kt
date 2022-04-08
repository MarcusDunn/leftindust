package com.leftindust.mockingbird.dao.entity

import com.leftindust.mockingbird.dao.entity.enums.RecordType
import com.leftindust.mockingbird.dao.entity.superclasses.AbstractJpaPersistable
import com.leftindust.mockingbird.graphql.types.input.GraphQLRecordInput
import java.sql.Timestamp
import java.time.Instant
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToOne

@Entity
class MediqRecord(
    @ManyToOne(cascade = [CascadeType.ALL])
    var patient: Patient,
    @Column(nullable = false)
    val creationDate: Timestamp,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: RecordType,
    @Column(length = 10_000, nullable = false)
    val jsonBlob: String,
) : AbstractJpaPersistable() {
    constructor(record: GraphQLRecordInput, patient: Patient) : this(
        patient = patient,
        creationDate = Timestamp.from(Instant.now()),
        type = record.recordType,
        jsonBlob = record.jsonBlob,
    )
}
