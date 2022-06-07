package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import java.sql.Date
import java.time.LocalDate
import javax.persistence.*

@Entity
class SurveyFieldEntity(
    val title: String,
    val number: Int,
    @Enumerated(value = EnumType.STRING)
    val surveyFieldType: SurveyFieldType,
    @ElementCollection
    val multiSelectPossibilities: List<String>?,
    val intUpperBound: Int?,
    val intLowerBound: Int?,
    val floatUpperBound: Float?,
    val floatLowerBound: Float?,
    val dateUpperBound: LocalDate?,
    val dateLowerBound: LocalDate?,
    val textRegex: String?,
    var jsonMetaData: String?,
) : AbstractJpaPersistable()