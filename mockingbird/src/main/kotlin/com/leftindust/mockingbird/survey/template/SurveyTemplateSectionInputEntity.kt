package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.EAGER

@Entity
class SurveyTemplateSectionInputEntity(
    val type: SurveyTemplateInputType,
    val label: String,
    @ElementCollection(fetch = EAGER)
    val options: MutableList<String>?,
    val placeholder: String?,
    val required: Boolean,
    val category: SurveyTemplateCategory,
    val uploadMultiple: Boolean?,
    val uploadAccept: TemplateInputUploadType?,
    val calculationId: Int
) : AbstractJpaPersistable()