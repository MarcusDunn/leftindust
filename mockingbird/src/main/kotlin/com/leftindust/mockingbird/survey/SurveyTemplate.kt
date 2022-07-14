package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import javax.persistence.CascadeType
import javax.persistence.OneToMany

class SurveyTemplate (
    val title: String,
    val subtitle: String?,
    @OneToMany
    val sections: MutableSet<CreateSurveyTemplateSection>,
    @OneToMany
    val calculations: MutableSet<CreateSurveyTemplateCalculation>,
): AbstractJpaPersistable();
