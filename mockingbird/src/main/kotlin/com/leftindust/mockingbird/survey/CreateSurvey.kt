package com.leftindust.mockingbird.survey

interface CreateSurvey {
    val name: String
    val sections: List<CreateSurveySection>
}