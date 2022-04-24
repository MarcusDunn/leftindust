package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import javax.persistence.Column
import javax.persistence.Entity


@Entity
class Email(
    @Column(nullable = false)
    var type: GraphQLEmailType,
    @Column(nullable = false)
    var email: String,
) : AbstractJpaPersistable() {
    constructor(graphQLEmail: GraphQLEmailInput) : this(graphQLEmail.type, graphQLEmail.email)
}