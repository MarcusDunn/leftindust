package com.leftindust.mockingbird.survey.complete

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class CompleteSurveySectionInputDataConverter(
    @Autowired
    val jacksonObjectMapper: ObjectMapper
) : AttributeConverter<CompleteSurveySectionInputData, String> {
    override fun convertToDatabaseColumn(attribute: CompleteSurveySectionInputData): String {
        return jacksonObjectMapper.writeValueAsString(attribute)
    }

    override fun convertToEntityAttribute(dbData: String): CompleteSurveySectionInputData {
        return jacksonObjectMapper.readValue(dbData, CompleteSurveySectionInputData::class.java)
    }
}