package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.patient.PatientDto
import javax.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
@Transactional
class ReadSurveyResponseServiceImpl(
    @Autowired private val formDataRepository: HibernateSurveyResponseRepository,
) : ReadSurveyResponseService {
    override suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): List<SurveyResponse>? {
        TODO()
    }
}