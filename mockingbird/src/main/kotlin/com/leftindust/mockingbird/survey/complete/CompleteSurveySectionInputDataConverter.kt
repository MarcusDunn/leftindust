package com.leftindust.mockingbird.survey.complete

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class CompleteSurveySectionInputDataConverter(
    @Autowired
    @Qualifier("jsonMapper")
    val jacksonObjectMapper: ObjectMapper
) : AttributeConverter<CompleteSurveySectionInputData, String> {
    override fun convertToDatabaseColumn(attribute: CompleteSurveySectionInputData): String {
        return jacksonObjectMapper.writeValueAsString(attribute)
    }

    override fun convertToEntityAttribute(dbData: String): CompleteSurveySectionInputData {
        return jacksonObjectMapper.readValue(dbData, CompleteSurveySectionInputData::class.java)
    }
}