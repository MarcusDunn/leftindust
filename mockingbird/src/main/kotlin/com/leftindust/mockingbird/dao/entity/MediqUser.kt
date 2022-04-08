package com.leftindust.mockingbird.dao.entity

import com.leftindust.mockingbird.dao.entity.superclasses.AbstractJpaPersistable
import com.leftindust.mockingbird.graphql.types.input.GraphQLUserInput
import javax.persistence.*

@Entity
class MediqUser(
    @Column(unique = true, nullable = false)
    val uniqueId: String,
    @OneToOne(cascade = [(CascadeType.ALL)], orphanRemoval = false)
    var group: MediqGroup? = null,
    @OneToOne(cascade = [CascadeType.ALL])
    var nameInfo: NameInfo,
) : AbstractJpaPersistable() {
    constructor(graphQLUserInput: GraphQLUserInput, group: MediqGroup?) : this(
        nameInfo = NameInfo(graphQLUserInput.nameInfo),
        uniqueId = graphQLUserInput.uid,
        group = group,
    )
}