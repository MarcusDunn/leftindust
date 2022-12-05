package com.leftindust.mockingbird.preference

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
class PreferenceEntity(
    @Column
    var key: PreferenceType,
    var value: Int
): AbstractJpaPersistable() {

}