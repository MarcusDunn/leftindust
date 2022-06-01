package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class NameInfo(
    @Column(nullable = false)
    var firstName: String,
    @Column(nullable = false)
    var lastName: String,
    var middleName: String? = null,
) : AbstractJpaPersistable()