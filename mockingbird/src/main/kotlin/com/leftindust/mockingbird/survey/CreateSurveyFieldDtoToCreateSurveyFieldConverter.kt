package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.LocalDateDtoToLocalDateConverter
import com.leftindust.mockingbird.extensions.doThenNull
import com.leftindust.mockingbird.graphql.types.LocalDateDto
import java.time.LocalDate
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class CreateSurveyFieldDtoToCreateSurveyFieldConverter(
    private val localDateDtoToLocalDateConverter: FallibleConverter<LocalDateDto, LocalDate>
) : FallibleConverter<CreateSurveyFieldDto, CreateSurveyField> {
    private val logger = KotlinLogging.logger { }

    override fun convert(source: CreateSurveyFieldDto): CreateSurveyField? {
        return when (source.surveyFieldType) {
            SurveyFieldType.SingleMultiSelect -> {
                val multiSelectPossibilities = source.multiSelectPossibilities
                    ?: return doThenNull { logger.debug { "could not create a CreateSingleMultiSelectSurveyField object from $source due to multiSelectPossibilities being null" } }
                object : CreateSurveyField.CreateSingleMultiSelectSurveyField {
                    override val multiSelectPossibilities = multiSelectPossibilities
                    override val title = source.title
                    override val number = source.number
                }
            }
            SurveyFieldType.MultiMultiSelect -> {
                val multiSelectPossibilities = source.multiSelectPossibilities
                    ?: return doThenNull { logger.debug { "could not create a CreateSingleMultiSelectSurveyField object from $source due to multiSelectPossibilities being null" } }
                object : CreateSurveyField.CreateMultiMultiSelectSurveyField {
                    override val multiSelectPossibilities = multiSelectPossibilities
                    override val title = source.title
                    override val number = source.number
                }
            }
            SurveyFieldType.Text -> {
                object : CreateSurveyField.CreateTextSurveyField {
                    override val regex = source.regex
                    override val title = source.title
                    override val number = source.number
                }
            }
            SurveyFieldType.Integer -> {
                object : CreateSurveyField.CreateIntegerSurveyField {
                    override val upperBound = source.integerUpperBound
                    override val lowerBound = source.integerLowerBound
                    override val title = source.title
                    override val number = source.number
                }
            }
            SurveyFieldType.Date -> {
                val upperBound = localDateDtoToLocalDateConverter.convert(source.dateUpperBound)
                    ?: return doThenNull { logger.debug { "could not create a CreateDateSurveyField object from $source due to upperBound failing to convert" } }
                val lowerBound = localDateDtoToLocalDateConverter.convert(source.dateLowerBound)
                    ?: return doThenNull { logger.debug { "could not create a CreateDateSurveyField object from $source due to upperBound failing to convert" } }
                object : CreateSurveyField.CreateDateSurveyField {
                    override val upperBound = upperBound
                    override val lowerBound = lowerBound
                    override val title = source.title
                    override val number = source.number
                }
            }
            SurveyFieldType.Float -> {
                object : CreateSurveyField.CreateFloatSurveyField {
                    override val upperBound = source.floatUpperBound
                    override val lowerBound = source.floatLowerBound
                    override val title = source.title
                    override val number = source.number
                }
            }
        }
    }
}

