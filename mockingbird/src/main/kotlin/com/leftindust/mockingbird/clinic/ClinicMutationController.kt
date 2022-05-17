package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.LogMessage
import com.leftindust.mockingbird.InfallibleConverter
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class ClinicMutationController(
    private val clinicCreationService: ClinicCreationService,
    private val clinicUpdaterService: UpdateClinicService,
    private val clinicDtoConverter: InfallibleConverter<Clinic, ClinicDto>,
) {
    private val logger = LoggerFactory.getLogger(ClinicMutationController::class.java)

    @MutationMapping
    suspend fun addClinic(@Argument clinic: CreateClinicDto): ClinicDto {
        when (val result = clinicCreationService.addClinic(clinic)) {
            is ClinicCreationService.ClinicCreationResult.Failure.DoctorIdsDoNotExist -> throw IllegalArgumentException(result.message)
            is ClinicCreationService.ClinicCreationResult.Success -> return clinicDtoConverter.convert(result.clinic)
        }
    }

    @MutationMapping
    suspend fun editClinic(@Argument clinic: ClinicEditDto): ClinicDto? {
        val editedClinic = clinicUpdaterService.editClinic(clinic)
        return if (editedClinic != null) {
            clinicDtoConverter.convert(editedClinic)
        } else {
            logger.trace(LogMessage("Returning null from ${ClinicMutationController::editClinic.name}", "${UpdateClinicService::class.simpleName}.${UpdateClinicService::editClinic.name} returned null").toString())
            null
        }
    }
}