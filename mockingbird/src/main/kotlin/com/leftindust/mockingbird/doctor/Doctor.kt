package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.email.Email
import com.leftindust.mockingbird.event.Event
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.user.MediqUser
import java.time.LocalDate
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Lob
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity
class Doctor(
    @OneToOne(optional = false)
    var nameInfo: NameInfo,
    @OneToMany
    var addresses: MutableSet<Address>,
    @OneToMany
    var emails: MutableSet<Email>,
    @OneToMany
    var phones: MutableSet<Phone>,
    @OneToOne
    var user: MediqUser?,
    @OneToMany(mappedBy = "doctor", cascade = [CascadeType.ALL], orphanRemoval = true)
    var events: MutableSet<DoctorEventEntity>,
    @Lob
    var thumbnail: ByteArray?,
    var title: String?,
    var dateOfBirth: LocalDate?,
    @OneToMany(mappedBy = "doctor", cascade = [CascadeType.ALL], orphanRemoval = true)
    var clinics: MutableSet<ClinicDoctorEntity>,
    @OneToMany(mappedBy = "doctor", cascade = [CascadeType.ALL], orphanRemoval = true)
    var patients: MutableSet<DoctorPatientEntity>,
) : AbstractJpaPersistable() {
    fun addPatient(patient: Patient) {
        val doctorPatientEntity = DoctorPatientEntity(this, patient)
        patients.add(doctorPatientEntity)
        patient.doctors.add(doctorPatientEntity)
    }

    fun removePatient(patient: Patient) {
        val doctorPatientEntity = DoctorPatientEntity(this, patient)
        patient.doctors.remove(doctorPatientEntity)
        patients.remove(doctorPatientEntity)
    }

    fun clearPatients() {
        patients.forEach { removePatient(it.patient) }
    }

    fun addEvent(event: Event) {
        val doctorEventEntity = DoctorEventEntity(this, event)
        event.doctors.add(doctorEventEntity)
        events.add(doctorEventEntity)
    }

    fun removeEvent(event: Event) {
        val doctorEventEntity = DoctorEventEntity(this, event)
        event.doctors.remove(doctorEventEntity)
        events.remove(doctorEventEntity)
    }
}






