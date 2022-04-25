package com.leftindust.mockingbird.patient

import com.expediagroup.graphql.generator.scalars.ID
import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.contact.EmergencyContact
import com.leftindust.mockingbird.doctor.Doctor
import com.leftindust.mockingbird.doctor.DoctorPatient
import com.leftindust.mockingbird.email.Email
import com.leftindust.mockingbird.event.Event
import com.leftindust.mockingbird.extensions.onUndefined
import com.leftindust.mockingbird.extensions.replaceAllIfNotNull
import com.leftindust.mockingbird.form.AssignedForm
import com.leftindust.mockingbird.person.*
import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.user.MediqUser
import java.sql.Date
import javax.persistence.*

@Entity
class Patient(
    nameInfo: NameInfo,
    addresses: Set<Address> = emptySet(),
    emails: Set<Email> = emptySet(),
    phones: Set<Phone> = emptySet(),
    schedule: Set<Event> = emptySet(),
    user: MediqUser? = null,
    thumbnail: String? = null,
    @Enumerated(EnumType.STRING)
    var sex: Sex,
    @Column(nullable = false)
    var dateOfBirth: Date,
    var gender: String = sex.toString(),
    @Enumerated(EnumType.STRING)
    var ethnicity: Ethnicity? = null,
    var insuranceNumber: String? = null,
    @OneToMany(
        mappedBy = "patient",
        cascade = [CascadeType.ALL],
    )
    var contacts: Set<EmergencyContact> = emptySet(),
    @OneToMany(mappedBy = "patient", orphanRemoval = true)
    var doctors: MutableSet<DoctorPatient> = mutableSetOf(),
    @OneToMany(mappedBy = "patient", orphanRemoval = true)
    var assignedForms: MutableCollection<AssignedForm> = mutableSetOf(),
) : Person(
    nameInfo = nameInfo,
    addresses = addresses.toMutableSet(),
    email = emails.toMutableSet(),
    phones = phones.toMutableSet(),
    user = user,
    events = schedule.toMutableSet(),
    thumbnail = thumbnail
) {

    /**
     * see [GraphQLPatientInput] for details on how updates should behave
     */
    constructor(
        graphQLPatientInput: GraphQLPatientInput,
        entityManager: EntityManager,
    ) : this(
        nameInfo = NameInfo(graphQLPatientInput.nameInfo),
        dateOfBirth = graphQLPatientInput.dateOfBirth.toDate(),
        insuranceNumber = graphQLPatientInput.insuranceNumber?.value,
        sex = graphQLPatientInput.sex,
        gender = graphQLPatientInput.gender ?: graphQLPatientInput.sex.toString(),
        ethnicity = graphQLPatientInput.ethnicity,
        phones = graphQLPatientInput.phones?.map { Phone(it) }?.toSet() ?: emptySet(),
        addresses = graphQLPatientInput.addresses?.map { Address(it) }?.toSet() ?: emptySet(),
        emails = graphQLPatientInput.emails?.map { Email(it) }?.toSet() ?: emptySet(),
        thumbnail = graphQLPatientInput.thumbnail,
    ) {

        contacts = graphQLPatientInput.emergencyContacts?.map { EmergencyContact(it, this) }?.toSet() ?: emptySet()

        graphQLPatientInput.doctors
            ?.map { did -> entityManager.find(Doctor::class.java, did.id) }
            ?.forEach { it.addPatient(this) }
    }

    fun addDoctor(doctor: Doctor): Patient {
        doctor.addPatient(this)
        return this
    }

    enum class SortableField {
        PID,
        FIRST_NAME,
        LAST_NAME,
        ;

        val fieldName: String
            get() {
                return when (this) {
                    PID -> Patient_.ID
                    FIRST_NAME -> "${Patient_.NAME_INFO}.${NameInfo_.FIRST_NAME}"
                    LAST_NAME -> "${Patient_.NAME_INFO}.${NameInfo_.LAST_NAME}"
                }
            }

        fun instanceValue(receiver: Patient): String {
            return when (this) {
                PID -> receiver.id!!.toString()
                FIRST_NAME -> receiver.nameInfo.firstName
                LAST_NAME -> receiver.nameInfo.firstName
            }
        }
    }


    @Throws(IllegalArgumentException::class)
    fun setByGqlInput(patientInput: GraphQLPatientEditInput, entityManager: EntityManager) {
        if (patientInput.pid.id != this.id) throw IllegalArgumentException("pid does not match entity, expected ${this.id} got ${patientInput.pid}")
        nameInfo.setByGqlInput(patientInput.nameInfo)
        dateOfBirth = patientInput.dateOfBirth?.toDate() ?: dateOfBirth
        addresses.replaceAllIfNotNull(patientInput.addresses?.map { Address(it) }?.toSet())
        email.replaceAllIfNotNull(patientInput.emails?.map { Email(it) }?.toSet())
        phones.replaceAllIfNotNull(patientInput.phones?.map { Phone(it) }?.toSet())
        insuranceNumber = patientInput.insuranceNumber.onUndefined(insuranceNumber?.let { ID(it) })?.value
        sex = patientInput.sex ?: sex
        gender = patientInput.gender ?: gender
        ethnicity = patientInput.ethnicity.onUndefined(ethnicity)
        thumbnail = patientInput.thumbnail.onUndefined(thumbnail)

        if (patientInput.doctors != null) {
            doctors
                .flatMap { doctorPatient -> doctorPatient.doctor.patients.filter { it.patient.id == this.id } }
                .forEach {
                    it.removeFromLists()
                    entityManager.remove(it)
                }

            assert(this.doctors.isEmpty())

            patientInput.doctors
                .map { entityManager.find(Doctor::class.java, it.id) }
                .forEach { it.addPatient(this) }
        }
    }

    override fun addEvent(eventEntity: Event) {
        events.add(eventEntity)
        eventEntity.patients.add(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Patient

        if (sex != other.sex) return false
        if (dateOfBirth != other.dateOfBirth) return false
        if (gender != other.gender) return false
        if (ethnicity != other.ethnicity) return false
        if (insuranceNumber != other.insuranceNumber) return false
        if (contacts != other.contacts) return false
        if (doctors != other.doctors) return false
        if (assignedForms != other.assignedForms) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + sex.hashCode()
        result = 31 * result + gender.hashCode()
        result = 31 * result + (ethnicity?.hashCode() ?: 0)
        result = 31 * result + (insuranceNumber?.hashCode() ?: 0)
        result = 31 * result + contacts.hashCode()
        result = 31 * result + doctors.hashCode()
        return result
    }
}