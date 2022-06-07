package com.leftindust.mockingbird.survey

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import com.leftindust.mockingbird.graphql.types.LocalDateDto
import java.util.*

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes(
    Type(SurveyFieldDto.IntegerSurveyFieldDto::class),
    Type(SurveyFieldDto.FloatSurveyFieldDto::class),
    Type(SurveyFieldDto.DateSurveyFieldDto::class),
    Type(SurveyFieldDto.MultiSelectSurveyFieldDto::class),
)
sealed class SurveyFieldDto(
    override val id: SurveyFieldDtoId,
    val title: String,
    val surveyFieldType: SurveyFieldType,
    val number: Int,
    val jsonMetaData: String,
) : AbstractGraphQLDto<SurveyFieldDto.SurveyFieldDtoId>() {

    data class SurveyFieldDtoId(override val value: UUID) : GraphQLID<UUID>

    class IntegerSurveyFieldDto(
        val upperBound: Int,
        val lowerBound: Int,
        id: SurveyFieldDtoId,
        title: String,
        surveyFieldType: SurveyFieldType,
        number: Int,
        jsonMetaData: String,
    ) : SurveyFieldDto(id, title, surveyFieldType, number, jsonMetaData)

    class FloatSurveyFieldDto(
        val upperBound: Float,
        val lowerBound: Float,
        id: SurveyFieldDtoId,
        title: String,
        surveyFieldType: SurveyFieldType,
        number: Int,
        jsonMetaData: String,
    ) : SurveyFieldDto(id, title, surveyFieldType, number, jsonMetaData)

    class DateSurveyFieldDto(
        val upperBound: LocalDateDto,
        val lowerBound: LocalDateDto,
        id: SurveyFieldDtoId,
        title: String,
        surveyFieldType: SurveyFieldType,
        number: Int,
        jsonMetaData: String,
    ) : SurveyFieldDto(id, title, surveyFieldType, number, jsonMetaData)

    class MultiSelectSurveyFieldDto(
        id: SurveyFieldDtoId,
        title: String,
        surveyFieldType: SurveyFieldType,
        number: Int,
        jsonMetaData: String,
    ) : SurveyFieldDto(id, title, surveyFieldType, number, jsonMetaData)

}