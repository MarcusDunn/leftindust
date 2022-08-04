package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import javax.persistence.Entity

@Entity
class CompleteSurveySectionInputEntity(
    val value: String
): AbstractJpaPersistable() {

}
