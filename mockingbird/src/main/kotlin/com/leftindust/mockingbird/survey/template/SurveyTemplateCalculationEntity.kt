package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import javax.persistence.Entity

@Entity
class SurveyTemplateCalculationEntity(
    val index: Int,
    val label: String,
    val inputType: SurveyTemplateInputType,
    val showOnComplete: Boolean,
    val calculation: String,
) : AbstractJpaPersistable()