package com.leftindust.mockingbird.survey.link

interface SurveyLinkValidityChecker {
    fun linkIsCurrentlyValid(surveyLink: SurveyLink): Boolean
}
