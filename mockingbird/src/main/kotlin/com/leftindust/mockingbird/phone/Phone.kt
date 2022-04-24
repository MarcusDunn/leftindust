package com.leftindust.mockingbird.phone

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class Phone(
    @Column(nullable = false)
    var number: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var type: GraphQLPhoneType
) : AbstractJpaPersistable() {
    constructor(graphQLPhone: GraphQLPhoneInput) : this(graphQLPhone.number, graphQLPhone.type)
}