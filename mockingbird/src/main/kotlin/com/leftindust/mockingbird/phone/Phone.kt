package com.leftindust.mockingbird.phone

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class Phone(
    @Column(nullable = false)
    var number: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var type: PhoneType,
) : AbstractJpaPersistable()