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
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Lob
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity
class Patient(
    @OneToOne(cascade = [CascadeType.PERSIST])
    var nameInfo: NameInfo,
    @OneToMany(cascade = [CascadeType.PERSIST])
    val addresses: MutableSet<Address>,
    @OneToMany(cascade = [CascadeType.PERSIST])
    val emails: MutableSet<Email>,
    @OneToMany(cascade = [CascadeType.PERSIST])
    val phones: MutableSet<Phone>,
    @OneToMany(mappedBy = "patient", cascade = [CascadeType.ALL], orphanRemoval = true)
    val events: MutableSet<PatientEventEntity> = mutableSetOf(),
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