package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.survey.link.SurveyLinkDto
import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionDto
import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionInputDto
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class CreateCompleteSurveyDtoToCreateCompleteSurveyConverter :
    FallibleConverter<CreateCompleteSurveyDto, CreateCompleteSurvey> {
    override fun convert(source: CreateCompleteSurveyDto): CreateCompleteSurvey? {
        return CreateCompleteSurveyImpl(
            surveyLinkId = source.surveyLinkId,
            completeSurveyTemplateSections = source.completeSurveyTemplateSections.map { createCompleteSurveySectionDto ->
                CreateCompleteSurveySectionImpl(
                    surveyTemplateSectionId = createCompleteSurveySectionDto.surveyTemplateSectionId,
                    completedSurveyInputs = createCompleteSurveySectionDto.completedSurveyInputs.map { createCompleteSurveySectioInputDto ->
                        CreateCompleteSurveyInputImpl(
                            surveyTemplateSectionInputId = createCompleteSurveySectioInputDto.surveyTemplateSectionInputId,
                            value = when (createCompleteSurveySectioInputDto.type) {
                                SurveyInputType.Number -> {
                                    JsonData.NumberValue(
                                        createCompleteSurveySectioInputDto.numberInput ?: return null
                                    )
                                }
                                SurveyInputType.String -> {
                                    JsonData.StringValue(
                                        createCompleteSurveySectioInputDto.stringInput ?: return null
                                    )
                                }
                                SurveyInputType.NumberArray -> {
                                    JsonData.NumberArray(createCompleteSurveySectioInputDto.numberArrayInput?.map {
                                        JsonData.NumberValue(it)
                                    } ?: return null)
                                }
                                SurveyInputType.StringArray -> {
                                    JsonData.StringArray(createCompleteSurveySectioInputDto.stringArrayInput?.map {
                                        JsonData.StringValue(it)
                                    } ?: return null)
                                }
                            }
                        )
                    }
                )
            }
        )
    }

    data class CreateCompleteSurveyImpl(
        override val surveyLinkId: SurveyLinkDto.SurveyLinkDtoId,
        override val completeSurveyTemplateSections: List<CreateCompleteSurveySection>
    ) : CreateCompleteSurvey

    data class CreateCompleteSurveySectionImpl(
        override val surveyTemplateSectionId: SurveyTemplateSectionDto.SurveyTemplateSectionDtoId,
        override val completedSurveyInputs: List<CreateCompleteSurveyInput>
    ) : CreateCompleteSurveySection

    data class CreateCompleteSurveyInputImpl(
        override val surveyTemplateSectionInputId: SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId,
        override val value: JsonData
    ) : CreateCompleteSurveyInput
}
