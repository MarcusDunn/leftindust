package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.group.MediqGroup
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToOne

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