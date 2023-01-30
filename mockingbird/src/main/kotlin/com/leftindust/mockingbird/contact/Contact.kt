package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.email.EmailEntity
import com.leftindust.mockingbird.patient.PatientEntity
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.person.NameInfoEntity
import com.leftindust.mockingbird.person.Relationship
import com.leftindust.mockingbird.phone.Phone
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

@Entity
class Contact(
    @ManyToOne(optional = false)
    var patientEntity: PatientEntity,
    @Enumerated(EnumType.STRING)
    var relationship: Relationship,
    @OneToOne
    var nameInfoEntity: NameInfoEntity,
    @OneToMany
    var phone: Set<Phone> = emptySet(),
    @OneToMany
    var email: Set<EmailEntity> = emptySet(),
) : AbstractJpaPersistable()