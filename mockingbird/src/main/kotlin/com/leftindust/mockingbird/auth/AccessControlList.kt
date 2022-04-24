package com.leftindust.mockingbird.auth

import com.leftindust.mockingbird.group.MediqGroup
import com.leftindust.mockingbird.user.MediqUser
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import javax.persistence.*

@Entity
class AccessControlList(
    @ManyToOne(cascade = [(CascadeType.ALL)])
    val group: MediqGroup? = null,
    @ManyToOne
    val mediqUser: MediqUser? = null,
    @ManyToOne(cascade = [(CascadeType.ALL)])
    val action: Action,
) : AbstractJpaPersistable() {
    override fun toString(): String {
        return "AccessControlList(group=$group, mediqUser=$mediqUser, action=$action)"
    }
}