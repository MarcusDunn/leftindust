package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.patient.Patient
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class SurveyResponse(
    @Column(length = 50_000)
    val dataJson: String,
    @ManyToOne
    val patient: Patient,
) : AbstractJpaPersistable()