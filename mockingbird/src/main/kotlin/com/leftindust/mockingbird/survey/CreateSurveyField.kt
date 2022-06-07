package com.leftindust.mockingbird.survey

import java.time.LocalDate

sealed interface CreateSurveyField {
    val title: String
    val surveyFieldType: SurveyFieldType
    val number: Int

    interface CreateSingleMultiSelectSurveyField : CreateSurveyField {
        val multiSelectPossibilities: List<String>
        override val surveyFieldType: SurveyFieldType
            get() = SurveyFieldType.SingleMultiSelect
    }

    interface CreateMultiMultiSelectSurveyField : CreateSurveyField {
        val multiSelectPossibilities: List<String>
        override val surveyFieldType: SurveyFieldType
            get() = SurveyFieldType.MultiMultiSelect
    }

    interface CreateIntegerSurveyField : CreateSurveyField {
        val upperBound: Int?
        val lowerBound: Int?
        override val surveyFieldType: SurveyFieldType
            get() = SurveyFieldType.Integer
    }

    interface CreateFloatSurveyField : CreateSurveyField {
        val upperBound: Float?
        val lowerBound: Float?
        override val surveyFieldType: SurveyFieldType
            get() = SurveyFieldType.Float
    }

    interface CreateDateSurveyField : CreateSurveyField {
        val upperBound: LocalDate?
        val lowerBound: LocalDate?
        override val surveyFieldType: SurveyFieldType
            get() = SurveyFieldType.Date
    }

    interface CreateTextSurveyField : CreateSurveyField {
        val regex: String
        override val surveyFieldType: SurveyFieldType
            get() = SurveyFieldType.Text
    }
}