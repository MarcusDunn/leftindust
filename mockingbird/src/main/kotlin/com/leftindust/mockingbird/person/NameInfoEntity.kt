package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class NameInfoEntity(
    @Column(nullable = false)
    override var firstName: String,
    @Column(nullable = false)
    override var lastName: String,
    override var middleName: String?,
) : AbstractJpaPersistable(), NameInfo