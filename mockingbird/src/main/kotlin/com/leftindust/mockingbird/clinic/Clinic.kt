package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.doctor.Doctor
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityManager
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany
import javax.persistence.OneToOne

@Entity
class Clinic(
    @Column(nullable = false)
    var name: String,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(nullable = false)
    var address: Address,
    @ManyToMany(mappedBy = "clinics")
    var doctors: MutableSet<Doctor>,
) : AbstractJpaPersistable() {
    fun setByGqlInput(clinic: GraphQLClinicEditInput, entityManager: EntityManager) {
        name = clinic.name ?: name
        clinic.address?.let { address.setByGqlInput(it) }
        doctors = clinic.doctors?.map { entityManager.find(Doctor::class.java, it.id) }?.toMutableSet() ?: doctors
    }

    constructor(gqlClinicInput: GraphQLClinicInput, entityManager: EntityManager) : this(
        name = gqlClinicInput.name,
        address = Address(gqlClinicInput.address),
        doctors = gqlClinicInput.doctors?.map { entityManager.find(Doctor::class.java, it.id) }?.toMutableSet() ?: mutableSetOf(),
    )
}