package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.person.Relationship
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.email.Email
import com.leftindust.mockingbird.patient.Patient
import javax.persistence.*

@Entity
class EmergencyContact(
    @ManyToOne(cascade = [CascadeType.MERGE])
    var patient: Patient,
    @Enumerated(EnumType.STRING)
    var relationship: Relationship,
    @OneToOne(cascade = [CascadeType.ALL])
    var nameInfo: NameInfo,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    var phone: Set<Phone> = emptySet(),
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    var email: Set<Email> = emptySet(),
) : AbstractJpaPersistable() {
    constructor(graphQLEmergencyContactInput: GraphQLEmergencyContactInput, patient: Patient) : this(
        patient = patient,
        relationship = graphQLEmergencyContactInput.relationship,
        nameInfo = NameInfo(
            firstName = graphQLEmergencyContactInput.firstName,
            middleName = graphQLEmergencyContactInput.middleName,
            lastName = graphQLEmergencyContactInput.lastName,
        ),
        email = graphQLEmergencyContactInput.emails?.map { Email(it) }?.toSet() ?: emptySet(),
        phone = graphQLEmergencyContactInput.phones?.map { Phone(it) }?.toSet() ?: emptySet(),
    )
}