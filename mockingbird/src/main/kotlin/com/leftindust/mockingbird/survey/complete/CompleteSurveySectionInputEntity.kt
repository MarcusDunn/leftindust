package com.leftindust.mockingbird.survey.complete

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.vladmihalcea.hibernate.type.json.JsonType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.Column
import javax.persistence.Entity

@Entity
@TypeDef(name = "json", typeClass = JsonType::class)
class CompleteSurveySectionInputEntity(
    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    val value: JsonData
): AbstractJpaPersistable() {

}

@JsonSubTypes(
    value = [
        JsonSubTypes.Type(
            JsonData.StringValue::class
        ),
        JsonSubTypes.Type(
            JsonData.NumberValue::class
        ),
        JsonSubTypes.Type(
            JsonData.StringArray::class
        ),
        JsonSubTypes.Type(
            JsonData.NumberArray::class
        )
    ]
)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, include = JsonTypeInfo.As.PROPERTY)
sealed interface JsonData {
    data class StringValue(
        val string: String
    ) : JsonData

    data class NumberValue(
        val number: Int
    ) : JsonData
    data class StringArray(
        val stringArray: List<StringValue>
    ) : JsonData

    data class NumberArray(
        val numberArray: List<NumberValue>
    ) : JsonData
}