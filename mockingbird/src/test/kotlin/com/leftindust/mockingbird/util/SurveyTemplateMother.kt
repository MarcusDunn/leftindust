package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.CreateSurveyTemplate
import com.leftindust.mockingbird.survey.CreateSurveyTemplateCalculationDto
import com.leftindust.mockingbird.survey.CreateSurveyTemplateCalculationDtoToCreateSurveyTemplateCalculationConverter
import com.leftindust.mockingbird.survey.CreateSurveyTemplateDto
import com.leftindust.mockingbird.survey.CreateSurveyTemplateDtoToCreateSurveyTemplateConverter
import com.leftindust.mockingbird.survey.CreateSurveyTemplateSectionDto
import com.leftindust.mockingbird.survey.CreateSurveyTemplateSectionDtoToCreateSurveyTemplateSectionConverter
import com.leftindust.mockingbird.survey.CreateSurveyTemplateSectionInputDto
import com.leftindust.mockingbird.survey.CreateSurveyTemplateSectionInputDtoToCreateSurveyTemplateSectionInputConverter
import com.leftindust.mockingbird.survey.CreateSurveyTemplateSectionInputEntity
import com.leftindust.mockingbird.survey.SurveyTemplate
import com.leftindust.mockingbird.survey.SurveyTemplateCalculationEntity
import com.leftindust.mockingbird.survey.SurveyTemplateCategory
import com.leftindust.mockingbird.survey.SurveyTemplateDto
import com.leftindust.mockingbird.survey.SurveyTemplateEntity
import com.leftindust.mockingbird.survey.SurveyTemplateInputType
import com.leftindust.mockingbird.survey.SurveyTemplateSectionEntity
import java.util.UUID

object SurveyTemplateMother {
    private val `koos knee survey template persisted id`: SurveyTemplateDto.Id
        get() = SurveyTemplateDto.Id(UUID.fromString("7032ff1d-d89e-4d8a-b4cb-12a731a41b89"))
    val `koos knee survey template dto`: SurveyTemplateDto
        get() = SurveyTemplateDto(
            id = `koos knee survey template persisted id`,
            title = `koos knee survey template entity unpersisted`.title
        )

    val `koos knee survey template`: SurveyTemplate
        get() = object : SurveyTemplate {
            override val id = `koos knee survey template persisted id`.value
            override val title = `create koos knee survey template title`
            override val subtitle = `create koos knee survey template subtitle`

        }

    val `koos knee survey template entity unpersisted`
        get() = SurveyTemplateEntity(
            title = `create koos knee survey template title`,
            subtitle = `create koos knee survey template subtitle`,
            sections = `create koos knee survey template dto sections`.mapIndexed { i, section ->
                SurveyTemplateSectionEntity(
                    index = i,
                    title = section.title,
                    subtitle = section.subtitle,
                    inputs = section.inputs.map {
                        CreateSurveyTemplateSectionInputEntity(
                            type = it.type,
                            label = it.label,
                            options = it.options?.toMutableList(),
                            placeholder = it.placeholder,
                            required = it.required,
                            category = it.category,
                            uploadMultiple = it.uploadMultiple,
                            uploadAccept = it.uploadAccept
                        )
                    }.toMutableSet()
                )
            }
                .toMutableSet(),
            calculations = `create koos knee survey template dto calculations`.mapIndexed { i, calculator ->
                SurveyTemplateCalculationEntity(
                    index = i,
                    label = calculator.label,
                    inputType = calculator.inputType,
                    showOnComplete = calculator.showOnComplete,
                    calculation = calculator.calculation
                )
            }
                .toMutableSet()
        )

    val `koos knee survey template entity persisted`
        get() = `koos knee survey template entity unpersisted`
            .apply { id = `koos knee survey template persisted id`.value }

    private val `create koos knee survey template title`: String
        get() = "KOOS knee survey"
    private val `create koos knee survey template subtitle`: String
        get() = "the knee'd to know about knees"
    private val `create koos knee survey template dto sections`: List<CreateSurveyTemplateSectionDto>
        get() = listOf(
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
    private val `create koos knee survey template dto calculations`: List<CreateSurveyTemplateCalculationDto>
        get() = emptyList()
    val `create koos knee survey template dto`: CreateSurveyTemplateDto
        get() = CreateSurveyTemplateDto(
            title = `create koos knee survey template title`,
            subtitle = `create koos knee survey template subtitle`,
            sections = `create koos knee survey template dto sections`,
            calculations = `create koos knee survey template dto calculations`,
        )

    val `create koos knee survey template`: CreateSurveyTemplate
        get() = CreateSurveyTemplateDtoToCreateSurveyTemplateConverter(
            CreateSurveyTemplateSectionDtoToCreateSurveyTemplateSectionConverter(
                CreateSurveyTemplateSectionInputDtoToCreateSurveyTemplateSectionInputConverter()
            ),
            CreateSurveyTemplateCalculationDtoToCreateSurveyTemplateCalculationConverter()
        )
            .convert(`create koos knee survey template dto`)!!
}