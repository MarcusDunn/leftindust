package com.leftindust.mockingbird.survey.template

import dev.forkhandles.result4k.onFailure
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class CreateSurveyTemplateServiceImpl(
    private val surveyTemplateRepository: SurveyTemplateRepository,
) : CreateSurveyTemplateService {
    override suspend fun createSurveyTemplate(surveyTemplate: CreateSurveyTemplate): SurveyTemplate {
        val newSurveyTemplate = SurveyTemplateEntity(
            title = surveyTemplate.title,
            subtitle = surveyTemplate.subtitle,
            sections = surveyTemplate.sections.mapIndexed { i, section -> createSectionToSectionEntity(section, i) }
                .toMutableSet(),
            calculations = surveyTemplate.calculations.mapIndexed { i, calculation ->
                createCalculationToCalculationEntity(calculation, i)
            }.toMutableSet(),
        )
        val surveyTemplateEntity = surveyTemplateRepository.save(newSurveyTemplate)
        return surveyTemplateEntity.toSurveyTemplate().onFailure { throw it.reason.toMockingbirdException() }
    }

    private fun createSectionToSectionEntity(
        createSurveyTemplateSection: CreateSurveyTemplateSection,
        index: Int
    ): SurveyTemplateSectionEntity {
        return SurveyTemplateSectionEntity(
            index = index,
            title = createSurveyTemplateSection.title,
            subtitle = createSurveyTemplateSection.subtitle,
            inputs = createSurveyTemplateSection.inputs.map {
                createSurveyTemplateSectionInputToSurveyTemplateSectionInputEntity(
                    it
                )
            }.toMutableSet(),
            calculationId = createSurveyTemplateSection.calculationId
        )
    }

    private fun createSurveyTemplateSectionInputToSurveyTemplateSectionInputEntity(createSurveyTemplateSectionInput: CreateSurveyTemplateSectionInput): SurveyTemplateSectionInputEntity {
        return when (val restriction = createSurveyTemplateSectionInput.restriction) {
            is CreateSurveyTemplateSectionInputRestriction.Date -> SurveyTemplateSectionInputEntity(
                type = SurveyTemplateInputType.Date,
                label = createSurveyTemplateSectionInput.label,
                options = null,
                placeholder = createSurveyTemplateSectionInput.placeholder,
                required = createSurveyTemplateSectionInput.required,
                category = createSurveyTemplateSectionInput.category,
                uploadMultiple = null,
                uploadAccept = null,
                calculationId = createSurveyTemplateSectionInput.calculationId
            )

            is CreateSurveyTemplateSectionInputRestriction.MultiSelect -> SurveyTemplateSectionInputEntity(
                type = SurveyTemplateInputType.MultiSelect,
                label = createSurveyTemplateSectionInput.label,
                options = restriction.options.toMutableList(),
                placeholder = createSurveyTemplateSectionInput.placeholder,
                required = createSurveyTemplateSectionInput.required,
                category = createSurveyTemplateSectionInput.category,
                uploadMultiple = null,
                uploadAccept = null,
                calculationId = createSurveyTemplateSectionInput.calculationId
            )

            is CreateSurveyTemplateSectionInputRestriction.Number -> SurveyTemplateSectionInputEntity(
                type = SurveyTemplateInputType.Number,
                label = createSurveyTemplateSectionInput.label,
                options = null,
                placeholder = createSurveyTemplateSectionInput.placeholder,
                required = createSurveyTemplateSectionInput.required,
                category = createSurveyTemplateSectionInput.category,
                uploadMultiple = null,
                uploadAccept = null,
                calculationId = createSurveyTemplateSectionInput.calculationId
            )

            is CreateSurveyTemplateSectionInputRestriction.Paragraph -> SurveyTemplateSectionInputEntity(
                type = SurveyTemplateInputType.Paragraph,
                label = createSurveyTemplateSectionInput.label,
                options = null,
                placeholder = createSurveyTemplateSectionInput.placeholder,
                required = createSurveyTemplateSectionInput.required,
                category = createSurveyTemplateSectionInput.category,
                uploadMultiple = null,
                uploadAccept = null,
                calculationId = createSurveyTemplateSectionInput.calculationId
            )

            is CreateSurveyTemplateSectionInputRestriction.SingleSelect -> SurveyTemplateSectionInputEntity(
                type = SurveyTemplateInputType.SingleSelect,
                label = createSurveyTemplateSectionInput.label,
                options = restriction.options.toMutableList(),
                placeholder = createSurveyTemplateSectionInput.placeholder,
                required = createSurveyTemplateSectionInput.required,
                category = createSurveyTemplateSectionInput.category,
                uploadMultiple = null,
                uploadAccept = null,
                calculationId = createSurveyTemplateSectionInput.calculationId
            )

            is CreateSurveyTemplateSectionInputRestriction.Text -> SurveyTemplateSectionInputEntity(
                type = SurveyTemplateInputType.Text,
                label = createSurveyTemplateSectionInput.label,
                options = null,
                placeholder = createSurveyTemplateSectionInput.placeholder,
                required = createSurveyTemplateSectionInput.required,
                category = createSurveyTemplateSectionInput.category,
                uploadMultiple = null,
                uploadAccept = null,
                calculationId = createSurveyTemplateSectionInput.calculationId
            )

            is CreateSurveyTemplateSectionInputRestriction.Title -> SurveyTemplateSectionInputEntity(
                type = SurveyTemplateInputType.Title,
                label = createSurveyTemplateSectionInput.label,
                options = null,
                placeholder = createSurveyTemplateSectionInput.placeholder,
                required = createSurveyTemplateSectionInput.required,
                category = createSurveyTemplateSectionInput.category,
                uploadMultiple = null,
                uploadAccept = null,
                calculationId = createSurveyTemplateSectionInput.calculationId
            )

            is CreateSurveyTemplateSectionInputRestriction.Upload -> SurveyTemplateSectionInputEntity(
                type = SurveyTemplateInputType.Upload,
                label = createSurveyTemplateSectionInput.label,
                options = null,
                placeholder = createSurveyTemplateSectionInput.placeholder,
                required = createSurveyTemplateSectionInput.required,
                category = createSurveyTemplateSectionInput.category,
                uploadMultiple = restriction.uploadMultiple,
                uploadAccept = restriction.uploadAccept,
                calculationId = createSurveyTemplateSectionInput.calculationId
            )
        }
    }

    private fun createCalculationToCalculationEntity(
        createSurveyTemplateCalculation: CreateSurveyTemplateCalculation,
        index: Int
    ): SurveyTemplateCalculationEntity {
        return SurveyTemplateCalculationEntity(
            index = index,
            label = createSurveyTemplateCalculation.label,
            inputType = createSurveyTemplateCalculation.type,
            showOnComplete = createSurveyTemplateCalculation.showOnComplete,
            calculation = createSurveyTemplateCalculation.calculation,
        )
    }
}
