package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class DoctorToDoctorDtoConverter : InfallibleConverter<Doctor, DoctorDto> {
    override fun convert(source: Doctor): DoctorDto {
        return DoctorDto(
            id = DoctorDto.DoctorDtoId(source.id),
            thumbnail = source.thumbnail,
            title = source.title,
            dateOfBirth = source.dateOfBirth,
        )
    }
}

