package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Controller

@Controller
class SurveyLinkToSurveyLinkDtoConverter : InfallibleConverter<SurveyLink, SurveyLinkDto> {
    override fun convert(source: SurveyLink): SurveyLinkDto {
        return SurveyLinkDto(
            id = SurveyLinkDto.SurveyLinkDtoId(source.id),
        )
    }
}