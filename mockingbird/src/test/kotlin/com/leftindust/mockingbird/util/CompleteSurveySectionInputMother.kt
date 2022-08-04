package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.complete.CompleteSurveySectionInputEntity
import com.leftindust.mockingbird.survey.complete.CompleteSurveySectionInputEntityToCompleteSurveySectionInputConverter
import com.leftindust.mockingbird.survey.complete.CompleteSurveySectionInputToCompleteSurveySectionInputDtoConverter
import java.util.UUID

object CompleteSurveySectionInputMother {
    private val completeSurveySectionInputEntityToCompleteSurveySectionInputConverter = CompleteSurveySectionInputEntityToCompleteSurveySectionInputConverter()
    private val completeSurveySectionInputToCompleteSurveySectionInputDtoConverter = CompleteSurveySectionInputToCompleteSurveySectionInputDtoConverter()
    object FilledOutHowBadIsThePainWhenIPokeIt {
        val id = UUID.fromString("6f88c023-4479-43b1-bc42-6018e48da7e5")
        val entityPersisted = CompleteSurveySectionInputEntity()
            .apply { id = this@FilledOutHowBadIsThePainWhenIPokeIt.id }
        val domain = completeSurveySectionInputEntityToCompleteSurveySectionInputConverter.convert(entityPersisted)
        val dto = completeSurveySectionInputToCompleteSurveySectionInputDtoConverter.convert(domain)
    }
}