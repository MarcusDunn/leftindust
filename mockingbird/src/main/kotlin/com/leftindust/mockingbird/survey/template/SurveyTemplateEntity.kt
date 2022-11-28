package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
class SurveyTemplateEntity(
    val title: String,
    val subtitle: String?,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    val sections: MutableSet<SurveyTemplateSectionEntity>,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    val calculations: MutableSet<SurveyTemplateCalculationEntity>,
) : AbstractJpaPersistable()
