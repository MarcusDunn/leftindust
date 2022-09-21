package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class CompleteSurveySectionEntityToCompleteSurveySectionConverter(
    private val completeSurveySectionInputEntityToCompleteSurveySectionInputConverter: InfallibleConverter<CompleteSurveySectionInputEntity, CompleteSurveySectionInput>
) : InfallibleConverter<CompleteSurveySectionEntity, CompleteSurveySection> {
    override fun convert(source: CompleteSurveySectionEntity): CompleteSurveySection {
        return CompleteSurveySectionImpl(
            id = source.id ?: throw NullEntityIdInConverterException(source),
            inputs = source.inputs.map {
                completeSurveySectionInputEntityToCompleteSurveySectionInputConverter.convert(it)
            }
        )
    }

    class CompleteSurveySectionImpl(override val id: UUID, override val inputs: List<CompleteSurveySectionInput>) : CompleteSurveySection

}