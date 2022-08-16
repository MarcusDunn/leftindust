package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class SurveyTemplateSectionEntity(
    val index: Int,
    val title: String,
    val subtitle: String?,
    @OneToMany(cascade = [CascadeType.ALL])
    val inputs: MutableSet<SurveyTemplateSectionInputEntity>,
    val calculationId: Int,
): AbstractJpaPersistable()