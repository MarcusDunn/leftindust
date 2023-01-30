package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import jakarta.persistence.Convert
import jakarta.persistence.Entity

@Entity
class CompleteSurveySectionInputEntity(
    @Convert(converter = CompleteSurveySectionInputDataConverter::class)
    val value: CompleteSurveySectionInputData
): AbstractJpaPersistable() {

}