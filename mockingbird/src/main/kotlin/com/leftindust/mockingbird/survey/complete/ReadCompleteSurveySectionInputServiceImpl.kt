package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.InfallibleConverter
import javax.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Transactional
@Service
class ReadCompleteSurveySectionInputServiceImpl(
    private val completeSurveySectionRepository: CompleteSurveySectionRepository,
    private val completeSurveySectionInputEntityToCompleteSurveySectionInputConverter: InfallibleConverter<CompleteSurveySectionInputEntity, CompleteSurveySectionInput>,
) : ReadCompleteSurveySectionInputService {
    override suspend fun completeSurveySectionInputByCompleteSurveySectionId(completeSurveySectionDtoId: CompleteSurveySectionDto.CompleteSurveySectionDtoId): List<CompleteSurveySectionInput>? {
        val completeSurveySectionEntity = completeSurveySectionRepository.findByIdOrNull(completeSurveySectionDtoId.value)
            ?: return null
        return completeSurveySectionEntity.inputs.map { completeSurveySectionInputEntityToCompleteSurveySectionInputConverter.convert(it) }
    }
}

