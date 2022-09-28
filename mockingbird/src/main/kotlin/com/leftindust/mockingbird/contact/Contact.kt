package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.email.EmailEntity
import com.leftindust.mockingbird.patient.PatientEntity
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.person.Relationship
import com.leftindust.mockingbird.phone.Phone
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity
class Contact(
    @ManyToOne(optional = false)
    var patient: PatientEntity, //Patient information should not be optional for a contact info
    @Enumerated(EnumType.STRING)
    var relationship: Relationship,
    @OneToOne
    var nameInfo: NameInfo,
    @OneToMany
    var phone: Set<Phone> = emptySet(),
    @OneToMany
    var email: Set<EmailEntity> = emptySet(),
) : AbstractJpaPersistable()