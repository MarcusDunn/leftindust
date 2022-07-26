package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.CreateSurveyTemplateSectionInputDto
import com.leftindust.mockingbird.survey.SurveyTemplateCategory
import com.leftindust.mockingbird.survey.SurveyTemplateInputType
import com.leftindust.mockingbird.survey.SurveyTemplateSectionInputEntity
import com.leftindust.mockingbird.survey.SurveyTemplateSectionInputEntityToSurveyTemplateSectionInputConverter
import com.leftindust.mockingbird.survey.SurveyTemplateSectionInputToSurveyTemplateSectionInputDtoConverter
import java.util.UUID

object SurveyTemplateSectionInputMother {
    val surveyTemplateSectionInputEntityToSurveyTemplateSectionInputConverter = SurveyTemplateSectionInputEntityToSurveyTemplateSectionInputConverter()
    val surveyTemplateSectionInputToSurveyTemplateSectionInputDtoConverter = SurveyTemplateSectionInputToSurveyTemplateSectionInputDtoConverter()

    object HowMuchPainAreYouInSectionInput {
        val id = UUID.fromString("9d642214-6c6d-4639-8209-5401acc244c1")
        val type = SurveyTemplateInputType.Number
        val label = "how much pain are you in?"
        val options: MutableList<String>? = null
        val placeholder = "between 0-10"
        val required = true
        val category = SurveyTemplateCategory.Body
        val uploadMultiple = null
        val uploadAccept = null

        val inputDto = CreateSurveyTemplateSectionInputDto(
            type = type,
            label = label,
            options = options,
            placeholder = placeholder,
            required = required,
            category = category,
            uploadMultiple = uploadMultiple,
            uploadAccept = uploadAccept,
        )

        val entityPersisted: SurveyTemplateSectionInputEntity = SurveyTemplateSectionInputEntity(
            type = inputDto.type,
            label = label,
            options = options,
            placeholder = placeholder,
            required = required,
            category = category,
            uploadMultiple = uploadMultiple,
            uploadAccept = uploadAccept,
        ).apply { this.id = this@HowMuchPainAreYouInSectionInput.id }

        val domain = surveyTemplateSectionInputEntityToSurveyTemplateSectionInputConverter.convert(entityPersisted)!!

        val dto = surveyTemplateSectionInputToSurveyTemplateSectionInputDtoConverter.convert(domain)
    }
}