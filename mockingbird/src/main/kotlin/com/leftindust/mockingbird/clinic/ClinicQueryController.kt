package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.LogMessage
import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.doctor.DoctorDto
import kotlinx.coroutines.flow.Flow
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class ClinicQueryController(
    private val readClinicService: ReadClinicService,
    private val clinicToClinicDtoConverter: InfallibleConverter<Clinic, ClinicDto>,
) {
    private val logger = LoggerFactory.getLogger(ClinicQueryController::class.java)

    @QueryMapping
    suspend fun clinicByClinicId(@Argument clinicId: ClinicDto.ClinicDtoId): ClinicDto? {
        val clinic = readClinicService.getByClinicId(clinicId)
        return if (clinic != null) {
            clinicToClinicDtoConverter.convert(clinic)
        } else {
            logger.trace(LogMessage("Returning null from clinicByCid", "ReadClinicService.getByCid returned null").toString())
            null
        }
    }
}