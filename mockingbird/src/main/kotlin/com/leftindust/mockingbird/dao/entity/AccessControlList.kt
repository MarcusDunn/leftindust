package com.leftindust.mockingbird.dao.entity

import com.leftindust.mockingbird.dao.entity.superclasses.AbstractJpaPersistable
import javax.persistence.*

@Entity
class AccessControlList(
    @ManyToOne(cascade = [(CascadeType.ALL)])
    val group: MediqGroup? = null,
    @ManyToOne
    val mediqUser: MediqUser? = null,
    @ManyToOne(cascade = [(CascadeType.ALL)])
    val action: Action,
) : AbstractJpaPersistable()