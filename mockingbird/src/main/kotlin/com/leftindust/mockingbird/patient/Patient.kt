package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.contact.Contact
import com.leftindust.mockingbird.doctor.DoctorPatientEntity
import com.leftindust.mockingbird.email.Email
import com.leftindust.mockingbird.event.Event
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.person.Sex
import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.user.MediqUser
import java.time.LocalDate
import javax.persistence.*

@Entity
class Patient(
    @OneToOne
    var nameInfo: NameInfo,
    @OneToMany
    var addresses: MutableSet<Address>,
    @OneToMany
    var emails: MutableSet<Email>,
    @OneToMany
    var phones: MutableSet<Phone>,
    @OneToMany(mappedBy = "patient", cascade = [CascadeType.ALL], orphanRemoval = true)
    var events: MutableSet<PatientEventEntity> = mutableSetOf(),
    @OneToOne
    var user: MediqUser?,
    @Lob
    var thumbnail: ByteArray?,
    @Column(nullable = false)
    var sex: Sex,
    @Column(nullable = false)
    var dateOfBirth: LocalDate,
    @Column(nullable = false)
    var gender: String = sex.toString(),
    var ethnicity: Ethnicity?,
    var insuranceNumber: String?,
    @OneToMany
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
        contact.patient = this
    }

    fun removeContact(contact: Contact) {
        contacts.remove(contact)
        contact.patient = null
    }
}