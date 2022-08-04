package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class CompleteSurveyEntity(
    @OneToMany(mappedBy = "completeSurvey")
    val sections: Set<CompleteSurveySectionEntity>
) : AbstractJpaPersistable() {

}
