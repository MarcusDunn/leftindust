package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.group.MediqGroup
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
class MediqUser(
    @Id
    val uniqueId: String,
    @ManyToOne
    var group: MediqGroup? = null,
    @OneToOne
    var nameInfo: NameInfo,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MediqUser

        if (uniqueId != other.uniqueId) return false

        return true
    }

    override fun hashCode(): Int {
        return uniqueId.hashCode()
    }
}