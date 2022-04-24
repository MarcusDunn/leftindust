package com.leftindust.mockingbird.dao.entity

import com.leftindust.mockingbird.dao.entity.superclasses.Person
import com.leftindust.mockingbird.extensions.replaceAllIfNotNull
import com.leftindust.mockingbird.graphql.types.input.GraphQLDoctorEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLDoctorInput
import com.leftindust.mockingbird.patient.Patient
import java.sql.Date
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityManager
import javax.persistence.ManyToMany
import javax.persistence.OneToMany

@Entity
class Doctor(
    nameInfo: NameInfo,
    addresses: Set<Address> = emptySet(),
    emails: Set<Email> = emptySet(),
    phones: Set<Phone> = emptySet(),
    user: MediqUser? = null,
    schedule: Set<Event> = emptySet(),
    @Column(nullable = true)
    var title: String? = null,
    @Column(nullable = true)
    var dateOfBirth: Date? = null,
    @ManyToMany(cascade = [CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST])
    var clinics: MutableSet<Clinic> = mutableSetOf(),
    @OneToMany(mappedBy = "patient", cascade = [CascadeType.ALL], orphanRemoval = true)
    var patients: MutableSet<DoctorPatient> = mutableSetOf(),
) : Person(
    nameInfo,
    addresses.toMutableSet(),
    emails.toMutableSet(),
    phones.toMutableSet(),
    user,
    schedule.toMutableSet()
) {
    constructor(
        graphQLDoctorInput: GraphQLDoctorInput,
        user: MediqUser?,
        patients: Collection<Patient>,
        clinics: Collection<Clinic> = emptySet()
    ) : this(
        nameInfo = NameInfo(graphQLDoctorInput.nameInfo),
        dateOfBirth = graphQLDoctorInput.dateOfBirth?.toDate(),
        addresses = graphQLDoctorInput.addresses?.map { Address(it) }?.toSet() ?: emptySet(),
        emails = graphQLDoctorInput.emails?.map { Email(it) }?.toSet() ?: emptySet(),
        phones = graphQLDoctorInput.phones?.map { Phone(it) }?.toSet() ?: emptySet(),
        user = user,
        clinics = clinics.toMutableSet(),
        title = graphQLDoctorInput.title,
    ) {
        patients.forEach { it.addDoctor(this) }
    }

    fun addPatient(patient: Patient): Doctor {
        val doctorPatient = DoctorPatient(doctor = this, patient = patient)
        patient.doctors.add(doctorPatient)
        this.patients.add(doctorPatient)
        return this
    }

    fun setByGqlInput(graphQLDoctorEditInput: GraphQLDoctorEditInput, entityManager: EntityManager, newUser: MediqUser? = null) {
        nameInfo.setByGqlInput(graphQLDoctorEditInput.nameInfo)
        dateOfBirth = graphQLDoctorEditInput.dateOfBirth?.toDate() ?: dateOfBirth
        addresses.replaceAllIfNotNull(graphQLDoctorEditInput.addresses?.map { Address(it) }?.toSet())
        email.replaceAllIfNotNull(graphQLDoctorEditInput.emails?.map { Email(it) }?.toSet())
        phones.replaceAllIfNotNull(graphQLDoctorEditInput.phones?.map { Phone(it) }?.toSet() ?: phones)
        user = newUser ?: user
        title = graphQLDoctorEditInput.title ?: title
        clinics.replaceAllIfNotNull(graphQLDoctorEditInput.clinics?.map { entityManager.find(Clinic::class.java, it.id) }
            ?.toSet() ?: clinics)
        if (graphQLDoctorEditInput.patients != null) {
            patients
                .flatMap { doctorPatient -> doctorPatient.patient.doctors.filter { it.doctor.id == this.id } }
                .forEach {
                    it.removeFromLists()
                    entityManager.remove(it)
                }

            assert(this.patients.isEmpty())

            graphQLDoctorEditInput.patients
                .map { entityManager.find(Patient::class.java, it.id) }
                .forEach { this.addPatient(it) }
        }
    }

    override fun addEvent(eventEntity: Event) {
        this.events.add(eventEntity)
        eventEntity.doctors.add(this)
    }
}






