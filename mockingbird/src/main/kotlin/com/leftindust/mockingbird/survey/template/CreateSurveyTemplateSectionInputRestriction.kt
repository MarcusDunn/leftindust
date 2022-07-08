package com.leftindust.mockingbird.survey.template

sealed interface CreateSurveyTemplateSectionInputRestriction {
    interface Text : CreateSurveyTemplateSectionInputRestriction {}
    interface Number : CreateSurveyTemplateSectionInputRestriction {
    }

    interface Date : CreateSurveyTemplateSectionInputRestriction {
    }

    interface Paragraph : CreateSurveyTemplateSectionInputRestriction {
    }

    interface Upload : CreateSurveyTemplateSectionInputRestriction {
        val uploadMultiple: Boolean
        val uploadAccept: TemplateInputUploadType
    }

    interface SingleSelect : CreateSurveyTemplateSectionInputRestriction {
        val options: List<String>
    }

    interface MultiSelect : CreateSurveyTemplateSectionInputRestriction {
        val options: List<String>
    }

    interface Title : CreateSurveyTemplateSectionInputRestriction {}
}