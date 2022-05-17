package com.leftindust.mockingbird.survey

interface CreateSurveySection {
    val name: String
    val number: Int
    val description: String
    val fields: List<CreateSurveyField>
}