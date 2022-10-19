package com.leftindust.mockingbird.survey.complete

fun CompleteSurvey.toCompleteSurveyDto(): CompleteSurveyDto {
    return CompleteSurveyDto(
        id = CompleteSurveyDto.CompleteSurveyDtoId(id)
    )
}