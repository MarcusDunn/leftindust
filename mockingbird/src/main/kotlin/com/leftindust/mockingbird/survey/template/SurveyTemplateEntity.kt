package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class SurveyTemplateEntity(
    var title: String,
    val subtitle: String?,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    val sections: MutableSet<SurveyTemplateSectionEntity>,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    val calculations: MutableSet<SurveyTemplateCalculationEntity>,
) : AbstractJpaPersistable()
