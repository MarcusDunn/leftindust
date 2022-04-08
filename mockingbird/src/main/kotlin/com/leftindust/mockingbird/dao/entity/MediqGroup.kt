package com.leftindust.mockingbird.dao.entity

import com.leftindust.mockingbird.dao.entity.superclasses.AbstractJpaPersistable
import com.leftindust.mockingbird.graphql.types.input.GraphQLGroupInput
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class MediqGroup(
    @Column(nullable = false)
    val name: String,
) : AbstractJpaPersistable() {
    constructor(graphQLGroupInput: GraphQLGroupInput) : this(name = graphQLGroupInput.name)
}