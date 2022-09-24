package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.survey.link.SurveyLinkDto
import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionDto
import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionInputDto
import org.springframework.stereotype.Component

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
                                    CompleteSurveySectionInputData.NumberValue(
                                        createCompleteSurveySectioInputDto.numberInput ?: return null
                                    )
                                }
                                SurveyInputType.String -> {
                                    CompleteSurveySectionInputData.StringValue(
                                        createCompleteSurveySectioInputDto.stringInput ?: return null
                                    )
                                }
                                SurveyInputType.NumberArray -> {
                                    CompleteSurveySectionInputData.NumberArray(createCompleteSurveySectioInputDto.numberArrayInput?.map {
                                        CompleteSurveySectionInputData.NumberValue(it)
                                    } ?: return null)
                                }
                                SurveyInputType.StringArray -> {
                                    CompleteSurveySectionInputData.StringArray(createCompleteSurveySectioInputDto.stringArrayInput?.map {
                                        CompleteSurveySectionInputData.StringValue(it)
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
        override val value: CompleteSurveySectionInputData
    ) : CreateCompleteSurveyInput
}
