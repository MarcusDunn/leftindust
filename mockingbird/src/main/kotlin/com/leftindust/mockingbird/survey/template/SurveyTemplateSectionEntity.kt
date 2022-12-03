package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
class SurveyTemplateSectionEntity(
    val index: Int,
    val title: String,
    val subtitle: String?,
    @OneToMany(cascade = [CascadeType.ALL])
    val inputs: MutableSet<SurveyTemplateSectionInputEntity>,
    val calculationId: Int,
): AbstractJpaPersistable()