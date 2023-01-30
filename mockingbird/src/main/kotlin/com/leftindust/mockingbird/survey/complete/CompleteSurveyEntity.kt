package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.survey.link.SurveyLinkEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

@Entity
class CompleteSurveyEntity(
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    val sections: Set<CompleteSurveySectionEntity>,
    @OneToOne
    val surveyLink: SurveyLinkEntity,
) : AbstractJpaPersistable() {

}
