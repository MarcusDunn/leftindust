package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.clinic.ClinicDto.ClinicDtoId
import org.springframework.stereotype.Component

@Component
class ClinicToClinicDtoConverter : InfallibleConverter<Clinic, ClinicDto> {
    override fun convert(source: Clinic): ClinicDto {
        return ClinicDto(
            id = ClinicDtoId(source.id),
            name = source.name
        )
    }
}