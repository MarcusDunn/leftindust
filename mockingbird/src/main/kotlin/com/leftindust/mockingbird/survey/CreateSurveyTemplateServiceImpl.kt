package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class CreateSurveyTemplateServiceImpl(
    private val surveyTemplateRepository: SurveyTemplateRepository,
    private val surveyTemplateEntityToSurveyTemplateConverter: InfallibleConverter<SurveyTemplateEntity, SurveyTemplate>,
) : CreateSurveyTemplateService {
    override suspend fun createSurveyTemplate(surveyTemplate: CreateSurveyTemplate): SurveyTemplate {
        val newSurveyTemplate = SurveyTemplateEntity(
            title = surveyTemplate.title,
            subtitle = surveyTemplate.subtitle,
            sections = surveyTemplate.sections.mapIndexed { i, section -> createSectionToSectionEntity(section, i) }
                .toMutableSet(),
            calculations = surveyTemplate.calculations.mapIndexed { i, calculation ->
                createCalculationToCalculationEntity(
                    calculation,
                    i
                )
            }.toMutableSet(),
        )
        val surveyTemplateEntity = surveyTemplateRepository.save(newSurveyTemplate)
        return surveyTemplateEntityToSurveyTemplateConverter.convert(surveyTemplateEntity)
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
        )
    }

    private fun createSurveyTemplateSectionInputToSurveyTemplateSectionInputEntity(createSurveyTemplateSectionInput: CreateSurveyTemplateSectionInput): CreateSurveyTemplateSectionInputEntity {
        return when (val restriction = createSurveyTemplateSectionInput.restriction) {
            is CreateSurveyTemplateSectionInputRestriction.Date -> CreateSurveyTemplateSectionInputEntity(
                type = SurveyTemplateInputType.Date,
                label = createSurveyTemplateSectionInput.label,
                options = null,
                placeholder = createSurveyTemplateSectionInput.placeholder,
                required = createSurveyTemplateSectionInput.required,
                category = createSurveyTemplateSectionInput.category,
                uploadMultiple = null,
                uploadAccept = null,
            )
            is CreateSurveyTemplateSectionInputRestriction.MultiSelect -> CreateSurveyTemplateSectionInputEntity(
                type = SurveyTemplateInputType.MultiSelect,
                label = createSurveyTemplateSectionInput.label,
                options = restriction.options.toMutableList(),
                placeholder = createSurveyTemplateSectionInput.placeholder,
                required = createSurveyTemplateSectionInput.required,
                category = createSurveyTemplateSectionInput.category,
                uploadMultiple = null,
                uploadAccept = null,
            )
            is CreateSurveyTemplateSectionInputRestriction.Number -> CreateSurveyTemplateSectionInputEntity(
                type = SurveyTemplateInputType.Number,
                label = createSurveyTemplateSectionInput.label,
                options = null,
                placeholder = createSurveyTemplateSectionInput.placeholder,
                required = createSurveyTemplateSectionInput.required,
                category = createSurveyTemplateSectionInput.category,
                uploadMultiple = null,
                uploadAccept = null,
            )
            is CreateSurveyTemplateSectionInputRestriction.Paragraph -> CreateSurveyTemplateSectionInputEntity(
                type = SurveyTemplateInputType.Paragraph,
                label = createSurveyTemplateSectionInput.label,
                options = null,
                placeholder = createSurveyTemplateSectionInput.placeholder,
                required = createSurveyTemplateSectionInput.required,
                category = createSurveyTemplateSectionInput.category,
                uploadMultiple = null,
                uploadAccept = null,
            )
            is CreateSurveyTemplateSectionInputRestriction.SingleSelect -> CreateSurveyTemplateSectionInputEntity(
                type = SurveyTemplateInputType.SingleSelect,
                label = createSurveyTemplateSectionInput.label,
                options = restriction.options.toMutableList(),
                placeholder = createSurveyTemplateSectionInput.placeholder,
                required = createSurveyTemplateSectionInput.required,
                category = createSurveyTemplateSectionInput.category,
                uploadMultiple = null,
                uploadAccept = null,
            )
            is CreateSurveyTemplateSectionInputRestriction.Text -> CreateSurveyTemplateSectionInputEntity(
                type = SurveyTemplateInputType.Text,
                label = createSurveyTemplateSectionInput.label,
                options = null,
                placeholder = createSurveyTemplateSectionInput.placeholder,
                required = createSurveyTemplateSectionInput.required,
                category = createSurveyTemplateSectionInput.category,
                uploadMultiple = null,
                uploadAccept = null,
            )
            is CreateSurveyTemplateSectionInputRestriction.Title -> CreateSurveyTemplateSectionInputEntity(
                type = SurveyTemplateInputType.Title,
                label = createSurveyTemplateSectionInput.label,
                options = null,
                placeholder = createSurveyTemplateSectionInput.placeholder,
                required = createSurveyTemplateSectionInput.required,
                category = createSurveyTemplateSectionInput.category,
                uploadMultiple = null,
                uploadAccept = null,
            )
            is CreateSurveyTemplateSectionInputRestriction.Upload -> CreateSurveyTemplateSectionInputEntity(
                type = SurveyTemplateInputType.Upload,
                label = createSurveyTemplateSectionInput.label,
                options = null,
                placeholder = createSurveyTemplateSectionInput.placeholder,
                required = createSurveyTemplateSectionInput.required,
                category = createSurveyTemplateSectionInput.category,
                uploadMultiple = restriction.uploadMultiple,
                uploadAccept = restriction.uploadAccept,
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