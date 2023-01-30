package com.leftindust.mockingbird.survey.complete

import dev.forkhandles.result4k.onFailure
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Transactional
@Service
class ReadCompleteSurveySectionInputServiceImpl(
    private val completeSurveySectionRepository: CompleteSurveySectionRepository,
) : ReadCompleteSurveySectionInputService {
    override suspend fun completeSurveySectionInputByCompleteSurveySectionId(completeSurveySectionDtoId: CompleteSurveySectionDto.CompleteSurveySectionDtoId): List<CompleteSurveySectionInput>? {
        val completeSurveySectionEntity = completeSurveySectionRepository.findByIdOrNull(completeSurveySectionDtoId.value)
            ?: return null
        return completeSurveySectionEntity.inputs.map { it.toCompleteSurveySectionInput().onFailure { throw it.reason.toMockingbirdException() }}
    }
}

