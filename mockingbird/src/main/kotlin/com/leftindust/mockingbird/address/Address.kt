package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.country.CountryState
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class Address(
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    var type: AddressType?,
    @Column(nullable = false)
    var address: String,
    @Column(nullable = false)
    var city: String,
    @Embedded
    var countryState: CountryState,
    @Column(nullable = false)
    var postalCode: String,
) : AbstractJpaPersistable()
