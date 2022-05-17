package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.extensions.doThenNull
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import javax.transaction.Transactional
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
@Transactional
class ReadSurveyServiceImpl(
    private val surveyRepository: HibernateSurveyRepository,
    private val patientService: ReadPatientService,
) : ReadSurveyService {
    private val logger = KotlinLogging.logger { }

    override suspend fun getBySurveyId(surveyId: SurveyDto.SurveyDtoId): Survey? {
        return surveyRepository.findById(surveyId.value).orElse(null)
    }

    override suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): Flow<Survey>? {
        val patient = patientService.getByPatientId(patientId)
            ?: return doThenNull { logger.debug { "returning null from getByPatientId for $patientId" } }
        return patient.patientFormEntities.map { it.survey }.asFlow()
    }
}