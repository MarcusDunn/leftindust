package com.leftindust.mockingbird.dao.entity

import com.leftindust.mockingbird.dao.entity.superclasses.AbstractJpaPersistable
import com.leftindust.mockingbird.graphql.types.GraphQLEmailType
import com.leftindust.mockingbird.graphql.types.input.GraphQLEmailInput
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