package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class CompleteSurveySectionInputEntityToCompleteSurveySectionInputConverter : InfallibleConverter<CompleteSurveySectionInputEntity, CompleteSurveySectionInput> {
    override fun convert(source: CompleteSurveySectionInputEntity): CompleteSurveySectionInput {
        return CompleteSurveySectionInputImpl(
            id = source.id ?: throw NullEntityIdInConverterException(source)
        )
    }

    class CompleteSurveySectionInputImpl(override val id: UUID) : CompleteSurveySectionInput {
    }
}