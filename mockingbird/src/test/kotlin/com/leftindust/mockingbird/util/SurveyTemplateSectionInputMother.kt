package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateSectionInputDto
import com.leftindust.mockingbird.survey.template.SurveyTemplateCategory
import com.leftindust.mockingbird.survey.template.SurveyTemplateInputType
import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionInputDto
import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionInputEntity
import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionInputEntityToSurveyTemplateSectionInputConverter
import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionInputToSurveyTemplateSectionInputDtoConverter
import java.util.UUID

object SurveyTemplateSectionInputMother {
    val surveyTemplateSectionInputEntityToSurveyTemplateSectionInputConverter = SurveyTemplateSectionInputEntityToSurveyTemplateSectionInputConverter()
    val surveyTemplateSectionInputToSurveyTemplateSectionInputDtoConverter = SurveyTemplateSectionInputToSurveyTemplateSectionInputDtoConverter()

    object HowMuchPainAreYouInSectionInput {
        val id = UUID.fromString("9d642214-6c6d-4639-8209-5401acc244c1")
        val graphqlId = SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId(id)
        val type = SurveyTemplateInputType.Number
        val label = "how much pain are you in?"
        val options: MutableList<String>? = null
        val placeholder = "between 0-10"
        val required = true
        val category = SurveyTemplateCategory.Body
        val uploadMultiple = null
        val uploadAccept = null
        val calculationId = 0

        val inputDto = CreateSurveyTemplateSectionInputDto(
            type = type,
            label = label,
            options = options,
            placeholder = placeholder,
            required = required,
            category = category,
            uploadMultiple = uploadMultiple,
            uploadAccept = uploadAccept,
            calculationId = 0,
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
            calculationId = calculationId
        ).apply { this.id = this@HowMuchPainAreYouInSectionInput.id }

        val entityDetached = SurveyTemplateSectionInputEntity(
            type = inputDto.type,
            label = label,
            options = options,
            placeholder = placeholder,
            required = required,
            category = category,
            uploadMultiple = uploadMultiple,
            uploadAccept = uploadAccept,
            calculationId = calculationId
        )

        val domain = surveyTemplateSectionInputEntityToSurveyTemplateSectionInputConverter.convert(entityPersisted)!!

        val dto = surveyTemplateSectionInputToSurveyTemplateSectionInputDtoConverter.convert(domain)
    }
}