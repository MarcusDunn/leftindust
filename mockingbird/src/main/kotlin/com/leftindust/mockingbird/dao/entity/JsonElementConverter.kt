package com.leftindust.mockingbird.dao.entity

import com.google.gson.JsonElement
import com.google.gson.JsonParser.parseString
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class JsonElementConverter : AttributeConverter<JsonElement, String> {
    override fun convertToDatabaseColumn(jsonElement: JsonElement): String {
        return jsonElement.toString()
    }

    override fun convertToEntityAttribute(string: String): JsonElement {
        return parseString(string)
    }
}