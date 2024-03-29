package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.address.AddressEntity
import com.leftindust.mockingbird.contact.Contact
import com.leftindust.mockingbird.doctor.DoctorEntity
import com.leftindust.mockingbird.doctor.DoctorPatientEntity
import com.leftindust.mockingbird.email.EmailEntity
import com.leftindust.mockingbird.event.Event
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.NameInfoEntity
import com.leftindust.mockingbird.person.Sex
import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.survey.link.SurveyLinkEntity
import com.leftindust.mockingbird.user.MediqUser
import java.time.LocalDate
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Lob
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

@Entity
class PatientEntity(
    @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    var nameInfoEntity: NameInfoEntity,
    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val addresses: MutableSet<AddressEntity>,
    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val emails: MutableSet<EmailEntity>,
    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val phones: MutableSet<Phone>,
    @OneToMany(mappedBy = "patient", cascade = [CascadeType.ALL], orphanRemoval = true)
    val events: MutableSet<PatientEventEntity>,
    @OneToMany(mappedBy = "patient")
    val assignedSurveys: MutableSet<SurveyLinkEntity>,
    @OneToOne
    var user: MediqUser?,
    @Lob
    var thumbnail: ByteArray?,
    @Column(nullable = false)
    var sex: Sex,
    @Column(nullable = false)
    var dateOfBirth: LocalDate,
    @Column
    var gender: String?,
    var ethnicity: Ethnicity?,
    var insuranceNumber: String?,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    var contacts: MutableSet<Contact>,
    @OneToMany(mappedBy = "patient", cascade = [CascadeType.ALL], orphanRemoval = true)
    var doctors: MutableSet<DoctorPatientEntity>,
) : AbstractJpaPersistable() {
    fun addEvent(event: Event) {
        val patientEvent = PatientEventEntity(this, event)
        events.add(patientEvent)
        patientEvent.event.patients.add(patientEvent)
    }
    fun removeEvent(event: Event) {
        val patientEvent = PatientEventEntity(this, event)
        events.remove(patientEvent)
        patientEvent.event.patients.remove(patientEvent)
    }

    fun addContact(contact: Contact) {
        contacts.add(contact)
        contact.patientEntity = this
    }

    fun removeContact(contact: Contact) {
        contacts.remove(contact)
    }

    fun addDoctor(doctor: DoctorEntity){
        val doctorPatientEntity = DoctorPatientEntity(doctor, this)
        this.doctors.add(doctorPatientEntity)
        doctor.patients.add(doctorPatientEntity)
    }

    fun removeDoctor(doctor: DoctorEntity){
        val doctorPatientEntity = DoctorPatientEntity(doctor, this)
        this.doctors.remove(doctorPatientEntity)
        doctor.patients.remove(doctorPatientEntity)
    }

    fun clearDoctors(){
        doctors.forEach{removeDoctor(it.doctor)}
    }

}