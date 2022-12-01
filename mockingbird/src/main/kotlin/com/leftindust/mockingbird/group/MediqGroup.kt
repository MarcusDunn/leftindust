package com.leftindust.mockingbird.group

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class MediqGroup(
    @Column(nullable = false)
    val name: String,
) : AbstractJpaPersistable()