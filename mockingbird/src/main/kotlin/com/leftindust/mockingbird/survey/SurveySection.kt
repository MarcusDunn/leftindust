package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import javax.persistence.*

@Entity
class SurveySection(
    @OneToMany
    val fields: Set<SurveyFieldEntity>,
    @Column(length = 50_000)
    val description: String?,
    val name: String,
    val number: Int,
) : AbstractJpaPersistable()
