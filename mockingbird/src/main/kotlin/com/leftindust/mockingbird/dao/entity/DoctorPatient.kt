package com.leftindust.mockingbird.dao.entity

import com.leftindust.mockingbird.dao.entity.superclasses.AbstractJpaPersistable
import java.sql.Timestamp
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.ManyToOne

// note that for the sake of persisting updates we treat the doctor as owning this table.
@Entity
class DoctorPatient(
    @ManyToOne
    var patient: Patient,
    @ManyToOne
    val doctor: Doctor,
    val dateCreated: Timestamp = Timestamp.from(Instant.now())
) : AbstractJpaPersistable() {
    fun removeFromLists() {
        patient.doctors.remove(this)
        doctor.patients.remove(this)
    }
}