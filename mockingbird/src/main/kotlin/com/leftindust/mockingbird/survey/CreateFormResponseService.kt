package com.leftindust.mockingbird.survey

import com.google.gson.JsonElement
import com.leftindust.mockingbird.patient.PatientDto

interface CreateFormResponseService {
    suspend fun attachForm(patient: PatientDto.PatientDtoId, formResponse: JsonElement): SurveyResponse?
}