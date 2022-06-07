package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.person.Relationship
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.email.Email
import com.leftindust.mockingbird.patient.Patient
import javax.persistence.*

@Entity
class Contact(
    @ManyToOne(optional = false)
    var patient: Patient?,
    @Enumerated(EnumType.STRING)
    var relationship: Relationship,
    @OneToOne
    var nameInfo: NameInfo,
    @OneToMany
    var phone: Set<Phone> = emptySet(),
    @OneToMany
    var email: Set<Email> = emptySet(),
) : AbstractJpaPersistable()