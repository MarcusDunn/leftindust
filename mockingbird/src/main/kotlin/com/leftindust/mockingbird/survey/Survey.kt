package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class Survey(
    var name: String,
    @OneToMany(orphanRemoval = true, cascade = [CascadeType.ALL])
    val sections: Set<SurveySection>,
) : AbstractJpaPersistable()
