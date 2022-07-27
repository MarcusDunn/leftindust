package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import org.springframework.stereotype.Component

@Component
class DoctorDtoConverter : InfallibleConverter<Doctor, DoctorDto> {
    override fun convert(source: Doctor): DoctorDto {
        return DoctorDto(
            id = DoctorDto.DoctorDtoId(source.id ?: throw NullEntityIdInConverterException(source)),
            firstName = source.nameInfo.firstName,
            middleName = source.nameInfo.middleName,
            lastName = source.nameInfo.lastName,
            thumbnail = source.thumbnail,
            title = source.title,
            dateOfBirth = source.dateOfBirth,
        )
    }
}

