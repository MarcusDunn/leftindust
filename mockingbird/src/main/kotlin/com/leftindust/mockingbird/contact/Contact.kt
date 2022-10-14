package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.email.EmailEntity
import com.leftindust.mockingbird.patient.PatientEntity
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.person.NameInfoEntity
import com.leftindust.mockingbird.person.Relationship
import com.leftindust.mockingbird.phone.Phone
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity
class Contact(
    @ManyToOne(optional = false, targetEntity = PatientEntity::class)
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
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