package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.graphql.types.LocalDateDto


data class CreateSurveyFieldDto(
    val title: String,
    val surveyFieldType: SurveyFieldType,
    val number: Int,
    val multiSelectPossibilities: List<String>?,
    val integerUpperBound: Int?,
    val integerLowerBound: Int?,
    val floatUpperBound: Float?,
    val floatLowerBound: Float?,
    val dateUpperBound: LocalDateDto,
    val dateLowerBound: LocalDateDto,
    val regex: String,
)