package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
class CompleteSurveySectionEntity(
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    val inputs: Set<CompleteSurveySectionInputEntity>
): AbstractJpaPersistable() {

}
