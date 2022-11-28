package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.country.CountryState
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class AddressEntity(
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: AddressType,
    @Column(nullable = false)
    var address: String,
    @Column(nullable = false)
    var city: String,
    @Embedded
    var countryState: CountryState,
    @Column(nullable = false)
    var postalCode: String,
) : AbstractJpaPersistable()

