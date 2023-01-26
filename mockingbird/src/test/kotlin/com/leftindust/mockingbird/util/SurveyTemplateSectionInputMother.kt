package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.template.*
import dev.forkhandles.result4k.onFailure
import java.util.*

object SurveyTemplateSectionInputMother {


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

        val domain =
            entityPersisted.toSurveyTemplateSectionInput().onFailure { throw it.reason.toMockingbirdException() }!!

        val dto = domain.toSurveyTemplateSectionInputDto().onFailure { throw it.reason.toMockingbirdException() }
    }
}