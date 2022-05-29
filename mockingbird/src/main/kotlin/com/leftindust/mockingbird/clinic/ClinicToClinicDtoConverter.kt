package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.clinic.ClinicDto.ClinicDtoId
import org.springframework.stereotype.Component

@Component
class ClinicToClinicDtoConverter : InfallibleConverter<Clinic, ClinicDto> {
    override fun convert(source: Clinic): ClinicDto {
        val clinicId = requireNotNull(source.id) { "Clinic id cannot be null" }
        return ClinicDto(
            id = ClinicDtoId(clinicId),
            name = source.name
        )
    }
}