package com.leftindust.mockingbird.dao.entity

import com.leftindust.mockingbird.dao.entity.enums.Relationship
import com.leftindust.mockingbird.dao.entity.superclasses.AbstractJpaPersistable
import com.leftindust.mockingbird.graphql.types.input.GraphQLEmergencyContactInput
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