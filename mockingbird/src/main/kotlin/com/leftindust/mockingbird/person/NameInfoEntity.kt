package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class NameInfoEntity(
    @Column(nullable = false)
    override var firstName: String,
    @Column(nullable = false)
    override var lastName: String,
    override var middleName: String?,
) : AbstractJpaPersistable(), NameInfo