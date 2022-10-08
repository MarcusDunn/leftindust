package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.survey.complete.CompleteSurveyEntity
import com.leftindust.mockingbird.survey.template.SurveyTemplateEntity
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
class SurveyLinkEntity(
    @ManyToOne
    val surveyTemplateEntity: SurveyTemplateEntity,
    @ManyToOne
    val patient: Patient,
    @OneToOne(optional = true)
    val completeSurvey: CompleteSurveyEntity?
) : AbstractJpaPersistable()