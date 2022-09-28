package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class EmailEntity(
    @Column(nullable = false)
    var type: EmailType,
    @Column(nullable = false)
    var address: String,
) : AbstractJpaPersistable()