package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.address.AddressEntity
import com.leftindust.mockingbird.email.EmailEntity
import com.leftindust.mockingbird.event.Event
import com.leftindust.mockingbird.patient.PatientEntity
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.person.NameInfoEntity
import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.preference.PreferenceEntity
import com.leftindust.mockingbird.user.MediqUser
import java.time.LocalDate
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.Lob
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

@Entity
class DoctorEntity(
    @OneToOne(optional = false)
    var nameInfoEntity: NameInfoEntity,
    @OneToMany
    var preferenceEntity: PreferenceEntity,
    @OneToMany
    var addresses: MutableSet<AddressEntity>,
    @OneToMany
    var emails: MutableSet<EmailEntity>,
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
    fun addPatient(patient: PatientEntity) {
        val doctorPatientEntity = DoctorPatientEntity(this, patient)
        patients.add(doctorPatientEntity)
        patient.doctors.add(doctorPatientEntity)
    }

    fun removePatient(patient: PatientEntity) {
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






