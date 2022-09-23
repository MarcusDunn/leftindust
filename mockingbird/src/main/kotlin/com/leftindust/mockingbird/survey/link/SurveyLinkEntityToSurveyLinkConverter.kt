package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class SurveyLinkEntityToSurveyLinkConverter : InfallibleConverter<SurveyLinkEntity, SurveyLink> {
    override fun convert(source: SurveyLinkEntity): SurveyLink {
        return SurveyLinkImpl(
            id = source.id ?: throw NullEntityIdInConverterException(source),
        )
    }

    private data class SurveyLinkImpl(
        override val id: UUID,
    ) : SurveyLink

}