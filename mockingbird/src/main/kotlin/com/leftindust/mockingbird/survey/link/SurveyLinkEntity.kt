package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.patient.PatientEntity
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.survey.template.SurveyTemplateEntity
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class SurveyLinkEntity(
    @ManyToOne
    val surveyTemplateEntity: SurveyTemplateEntity,
    @ManyToOne
    val patient: PatientEntity
) : AbstractJpaPersistable()