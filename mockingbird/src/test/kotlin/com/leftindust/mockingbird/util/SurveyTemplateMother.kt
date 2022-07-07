package com.leftindust.mockingbird.util

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
import com.leftindust.mockingbird.survey.SurveyTemplateSection
import com.leftindust.mockingbird.survey.SurveyTemplateSectionEntity
import java.util.UUID

object SurveyTemplateMother {
    val `koos knee survey template sections`: List<SurveyTemplateSection>
        get() = `koos knee survey template entity persisted`.sections.map {
            object : SurveyTemplateSection {
                override val id = it.id!!
                override val title = it.title
                override val subtitle = it.subtitle
            }
        }

    val `koos knee survey template`
        get() = object : SurveyTemplate {
            override val id = `koos knee survey template persisted id`
            override val title = `create koos knee survey template title`
            override val subtitle = `create koos knee survey template subtitle`
        }

    private val `koos knee survey template persisted id` = UUID.fromString("7032ff1d-d89e-4d8a-b4cb-12a731a41b89")
    val `koos knee survey template dto`
        get() = SurveyTemplateDto(
            id = SurveyTemplateDto.SurveyTemplateDtoId(`koos knee survey template persisted id`),
            title = `create koos knee survey template title`,
            subtitle = `create koos knee survey template subtitle`,
        )

    val `koos knee survey template entity persisted`
        get() = SurveyTemplateEntity(
            `create koos knee survey template title`,
            `create koos knee survey template subtitle`,
            `create koos knee survey template dto sections`.mapIndexed { i, section ->
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
                ).apply { id = UUID.nameUUIDFromBytes("${i}${section.hashCode()}".encodeToByteArray()) }
            }
                .toMutableSet(),
            `create koos knee survey template dto calculations`.mapIndexed { i, calculator ->
                SurveyTemplateCalculationEntity(
                    index = i,
                    label = calculator.label,
                    inputType = calculator.inputType,
                    showOnComplete = calculator.showOnComplete,
                    calculation = calculator.calculation
                )
            }
                .toMutableSet()
        ).apply { id = `koos knee survey template persisted id` }

    val `koos knee survey template entity unpersisted`
        get() = `koos knee survey template entity persisted`
            .apply {
                id = null
                sections.forEach { it.id = null }
            }

    private val `create koos knee survey template title` = "KOOS knee survey"
    private val `create koos knee survey template subtitle` = "the knee'd to know about knees"
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
        title = `create koos knee survey template title`,
        subtitle = `create koos knee survey template subtitle`,
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