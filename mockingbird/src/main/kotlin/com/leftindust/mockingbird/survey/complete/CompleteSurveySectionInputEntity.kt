package com.leftindust.mockingbird.survey.complete

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.AttributeConverter
import javax.persistence.Convert
import javax.persistence.Converter
import javax.persistence.Entity

@Entity
class CompleteSurveySectionInputEntity(
    @Convert(converter = CompleteSurveySectionInputDataConverter::class)
    val value: CompleteSurveySectionInputData
): AbstractJpaPersistable() {

}