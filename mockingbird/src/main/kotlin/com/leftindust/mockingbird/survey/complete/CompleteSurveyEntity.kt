package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.survey.link.SurveyLinkEntity
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
class CompleteSurveyEntity(
    @OneToMany
    val sections: Set<CompleteSurveySectionEntity>,
    @ManyToOne
    val surveyLink: SurveyLinkEntity,
) : AbstractJpaPersistable() {

}
