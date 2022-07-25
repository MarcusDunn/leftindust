package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class SurveyTemplateSectionInputEntityToSurveyTemplateSectionInputConverter : InfallibleConverter<SurveyTemplateSectionInputEntity, SurveyTemplateSectionInput> {
    override fun convert(source: SurveyTemplateSectionInputEntity): SurveyTemplateSectionInput {
        return SurveyTemplateSectionInputImpl(
            id = source.id ?: throw NullEntityIdInConverterException(source)
        )
    }

    data class SurveyTemplateSectionInputImpl(
        override val id: UUID,
    ) : SurveyTemplateSectionInput
}