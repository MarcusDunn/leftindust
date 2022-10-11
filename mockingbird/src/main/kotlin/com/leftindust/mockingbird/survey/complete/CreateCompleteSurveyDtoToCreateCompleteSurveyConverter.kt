package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.ConversionError.Companion.ConversionFailure
import com.leftindust.mockingbird.survey.link.SurveyLinkDto
import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionDto
import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionInputDto
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success

fun CreateCompleteSurveyDto.toCreateCompleteSurvey(): Result4k<CreateCompleteSurvey, ConversionError<CreateCompleteSurveyDto, CreateCompleteSurvey>> {
    return Success(CreateCompleteSurveyImpl(
        surveyLinkId = surveyLinkId,
        completeSurveyTemplateSections = completeSurveyTemplateSections.map { createCompleteSurveySectionDto ->
            CreateCompleteSurveySectionImpl(
                surveyTemplateSectionId = createCompleteSurveySectionDto.surveyTemplateSectionId,
                completedSurveyInputs = createCompleteSurveySectionDto.completedSurveyInputs.map { createCompleteSurveySectionInputDto ->
                    CreateCompleteSurveyInputImpl(
                        surveyTemplateSectionInputId = createCompleteSurveySectionInputDto.surveyTemplateSectionInputId,
                        value = when (createCompleteSurveySectionInputDto.type) {
                            SurveyInputType.Number -> {
                                CompleteSurveySectionInputData.NumberValue(
                                    createCompleteSurveySectionInputDto.numberInput ?: return ConversionFailure(
                                        Exception("type was Number but numberInput was null")
                                    )
                                )
                            }

                            SurveyInputType.String -> {
                                CompleteSurveySectionInputData.StringValue(
                                    createCompleteSurveySectionInputDto.stringInput ?: return ConversionFailure(
                                        Exception("type was String but stringInput was null")
                                    )
                                )
                            }

                            SurveyInputType.NumberArray -> {
                                CompleteSurveySectionInputData.NumberArray(createCompleteSurveySectionInputDto.numberArrayInput?.map {
                                    CompleteSurveySectionInputData.NumberValue(it)
                                }
                                    ?: return ConversionFailure(Exception("type was NumberArray but numberArrayInput was null")))
                            }

                            SurveyInputType.StringArray -> {
                                CompleteSurveySectionInputData.StringArray(createCompleteSurveySectionInputDto.stringArrayInput?.map {
                                    CompleteSurveySectionInputData.StringValue(it)
                                }
                                    ?: return ConversionFailure(Exception("type was StringArray but stringArrayInput was null")))
                            }
                        }
                    )
                }
            )
        }
    ))
}

private data class CreateCompleteSurveyImpl(
    override val surveyLinkId: SurveyLinkDto.SurveyLinkDtoId,
    override val completeSurveyTemplateSections: List<CreateCompleteSurveySection>,
) : CreateCompleteSurvey

private data class CreateCompleteSurveySectionImpl(
    override val surveyTemplateSectionId: SurveyTemplateSectionDto.SurveyTemplateSectionDtoId,
    override val completedSurveyInputs: List<CreateCompleteSurveyInput>,
) : CreateCompleteSurveySection

private data class CreateCompleteSurveyInputImpl(
    override val surveyTemplateSectionInputId: SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId,
    override val value: CompleteSurveySectionInputData,
) : CreateCompleteSurveyInput