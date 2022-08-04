package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class CompleteSurveySectionEntity(
    @OneToMany
    val inputs: Set<CompleteSurveySectionInputEntity>
): AbstractJpaPersistable() {

}
