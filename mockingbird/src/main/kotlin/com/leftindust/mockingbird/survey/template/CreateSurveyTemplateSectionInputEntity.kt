package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import javax.persistence.ElementCollection
import javax.persistence.Entity

@Entity
class CreateSurveyTemplateSectionInputEntity(
    val type: SurveyTemplateInputType,
    val label: String,
    @ElementCollection
    val options: MutableList<String>?,
    val placeholder: String?,
    val required: Boolean,
    val category: SurveyTemplateCategory,
    val uploadMultiple: Boolean?,
    val uploadAccept: TemplateInputUploadType?,
) : AbstractJpaPersistable()