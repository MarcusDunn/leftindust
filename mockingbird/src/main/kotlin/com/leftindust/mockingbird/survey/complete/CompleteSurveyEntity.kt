package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.survey.link.SurveyLinkEntity
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity
class CompleteSurveyEntity(
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    val sections: Set<CompleteSurveySectionEntity>,
    @OneToOne
    val surveyLink: SurveyLinkEntity,
) : AbstractJpaPersistable() {

}
