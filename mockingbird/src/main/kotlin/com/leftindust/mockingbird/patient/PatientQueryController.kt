package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.graphql.types.input.RangeDto
import dev.forkhandles.result4k.onFailure
import mu.KotlinLogging
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class PatientQueryController(
    private val readPatientService: ReadPatientService,
) {
    private val logger = KotlinLogging.logger { }

    @QueryMapping("patientsByRange")
    suspend fun patientsByRange(@Argument("range") range: RangeDto): List<PatientDto> {
        return readPatientService.getMany(range)
            .map { it.toPatientDto().onFailure { throw it.reason.toMockingbirdException() } }
    }

    @QueryMapping("patientsByPatientId")
    suspend fun patientsByPatientId(@Argument("patientIds") patientIds: List<PatientDto.PatientDtoId>): List<PatientDto?> {
        return patientIds
            .map { readPatientService.getByPatientId(it) }
            .map {
                it?.let { patient ->
                    patient.toPatientDto().onFailure { throw it.reason.toMockingbirdException() }
                }
            }
    }

    suspend fun patientsByExample(example: PatientExampleDto): List<PatientDto> {
        return readPatientService.searchByExample(example)
            .map { it.toPatientDto().onFailure { throw it.reason.toMockingbirdException() } }
    }
}
