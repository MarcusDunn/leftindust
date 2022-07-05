package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.CreateSurveyTemplateCalculationDto
import com.leftindust.mockingbird.survey.CreateSurveyTemplateCalculationDtoToCreateSurveyTemplateCalculationConverter
import com.leftindust.mockingbird.survey.CreateSurveyTemplateDto
import com.leftindust.mockingbird.survey.CreateSurveyTemplateDtoToCreateSurveyTemplateConverter
import com.leftindust.mockingbird.survey.CreateSurveyTemplateSectionDto
import com.leftindust.mockingbird.survey.CreateSurveyTemplateSectionDtoToCreateSurveyTemplateSectionConverter
import com.leftindust.mockingbird.survey.CreateSurveyTemplateSectionInputDto
import com.leftindust.mockingbird.survey.CreateSurveyTemplateSectionInputDtoToCreateSurveyTemplateSectionInputConverter
import com.leftindust.mockingbird.survey.SurveyTemplateCategory
import com.leftindust.mockingbird.survey.SurveyTemplateDto
import com.leftindust.mockingbird.survey.SurveyTemplateEntity
import com.leftindust.mockingbird.survey.SurveyTemplateInputType
import java.util.UUID

object SurveyTemplateMother {
    private val `koos knee survey template persisted id` = UUID.fromString("7032ff1d-d89e-4d8a-b4cb-12a731a41b89")
    val `koos knee survey template dto` = SurveyTemplateDto(
        id = `koos knee survey template persisted id`,
    )

    val `koos knee survey template entity unpersisted`
        get() = SurveyTemplateEntity()

    val `koos knee survey template entity persisted` = `koos knee survey template entity unpersisted`
        .apply { id = `koos knee survey template persisted id` }

    private val `create koos knee survey template dto title` = "KOOS knee survey"
    private val `create koos knee survey template dto subtitle` = "the knee'd to know about knees"
    private val `create koos knee survey template dto sections` = listOf(
        CreateSurveyTemplateSectionDto(
            title = "Section the first!",
            subtitle = "The first section",
            inputs = listOf(
                CreateSurveyTemplateSectionInputDto(
                    type = SurveyTemplateInputType.Number,
                    label = "how much pain are you in?",
                    options = null,
                    placeholder = "between 0-10",
                    required = true,
                    category = SurveyTemplateCategory.Body,
                    uploadMultiple = null,
                    uploadAccept = null,
                ),
            ),
        )
    )
    private val `create koos knee survey template dto calculations` = emptyList<CreateSurveyTemplateCalculationDto>()
    val `create koos knee survey template dto` = CreateSurveyTemplateDto(
        title = `create koos knee survey template dto title`,
        subtitle = `create koos knee survey template dto subtitle`,
        sections = `create koos knee survey template dto sections`,
        calculations = `create koos knee survey template dto calculations`,
    )

    val `create koos knee survey template` = CreateSurveyTemplateDtoToCreateSurveyTemplateConverter(
        CreateSurveyTemplateSectionDtoToCreateSurveyTemplateSectionConverter(
            CreateSurveyTemplateSectionInputDtoToCreateSurveyTemplateSectionInputConverter()
        ),
        CreateSurveyTemplateCalculationDtoToCreateSurveyTemplateCalculationConverter()
    )
        .convert(`create koos knee survey template dto`)!!
}