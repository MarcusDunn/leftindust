package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.visit.Visit
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.doctor.DoctorEventEntity
import com.leftindust.mockingbird.patient.PatientEventEntity
import javax.persistence.*

@Entity
class Event(
    var ical: String,
    @OneToMany(mappedBy = "event", cascade = [CascadeType.ALL], orphanRemoval = true)
    val doctors: MutableSet<DoctorEventEntity>,
    @OneToMany(mappedBy = "event", cascade = [CascadeType.ALL], orphanRemoval = true)
    val patients: MutableSet<PatientEventEntity>,
    @OneToOne(optional = true, mappedBy = "event")
    var visit: Visit?,
) : AbstractJpaPersistable() {
    fun removeAllPatients() {
        patients.forEach { it.patient.removeEvent(this) }
    }

    fun removeAllDoctors() {
        patients.forEach { it.patient.removeEvent(this) }
    }
}