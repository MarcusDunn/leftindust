package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.patient.PatientEntity
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.survey.complete.CompleteSurveyEntity
import com.leftindust.mockingbird.survey.template.SurveyTemplateEntity
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne

@Entity
class SurveyLinkEntity(
    @ManyToOne
    val surveyTemplateEntity: SurveyTemplateEntity,
    @ManyToOne
    val patient: PatientEntity,
    @OneToOne(optional = true)
    var completeSurvey: CompleteSurveyEntity?
) : AbstractJpaPersistable() {
    fun addCompleteSurvey(survey: CompleteSurveyEntity) {
        this.completeSurvey = survey
    }
}