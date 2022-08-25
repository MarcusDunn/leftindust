package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId
import java.util.UUID

data class SurveyTemplateSectionInputDto(
    override val id: SurveyTemplateSectionInputDtoId,
    val type: SurveyTemplateInputType,
    val label: String,
    val options: List<String>?,
    val placeholder: String?,
    val required: Boolean,
    val category: SurveyTemplateCategory,
    val uploadMultiple: Boolean?,
    val uploadAccept: TemplateInputUploadType?,
    val calculationId: Int,
) : AbstractGraphQLDto<SurveyTemplateSectionInputDtoId>() {
    data class SurveyTemplateSectionInputDtoId(override val value: UUID) : GraphQLID<UUID>
}