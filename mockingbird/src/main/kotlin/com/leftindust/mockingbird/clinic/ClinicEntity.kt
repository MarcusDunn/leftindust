@file:JvmName("ClinicKt")

package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.doctor.ClinicDoctorEntity
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity
class ClinicEntity(
    @Column(nullable = false)
    var name: String,
    @OneToOne(optional = false, cascade = [CascadeType.PERSIST])
    var address: Address,
    @OneToMany(mappedBy = "clinic")
    var doctors: MutableSet<ClinicDoctorEntity> = mutableSetOf(),
) : AbstractJpaPersistable()