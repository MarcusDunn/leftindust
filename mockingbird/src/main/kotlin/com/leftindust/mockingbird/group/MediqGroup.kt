package com.leftindust.mockingbird.group

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class MediqGroup(
    @Column(nullable = false)
    val name: String,
) : AbstractJpaPersistable() {
    constructor(graphQLGroupInput: GraphQLGroupInput) : this(name = graphQLGroupInput.name)
}