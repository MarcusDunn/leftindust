package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class CompleteSurveySectionEntity(
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    val inputs: Set<CompleteSurveySectionInputEntity>
): AbstractJpaPersistable() {

}
