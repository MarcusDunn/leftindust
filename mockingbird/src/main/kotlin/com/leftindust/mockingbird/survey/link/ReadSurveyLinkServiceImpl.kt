package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.PatientRepository
import javax.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class ReadSurveyLinkServiceImpl(
    private val surveyLinkRepository: SurveyLinkRepository,
    private val patientRepository: PatientRepository,
    private val surveyLinkEntityToSurveyLinkConverter: InfallibleConverter<SurveyLinkEntity, SurveyLink>
) : ReadSurveyLinkService {
    override suspend fun getBySurveyLinkId(surveyLinkId: SurveyLinkDto.SurveyLinkDtoId): SurveyLink? {
        val surveyLinkEntity = surveyLinkRepository.findByIdOrNull(surveyLinkId.value)
            ?: return null
        return surveyLinkEntityToSurveyLinkConverter.convert(surveyLinkEntity)
    }

    override suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): List<SurveyLink>? {
        val patientEntity = patientRepository.findByIdOrNull(patientId.value)
            ?: return null
        return patientEntity.assignedSurveys.map { surveyLinkEntityToSurveyLinkConverter.convert(it) }
    }
}