package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.graphql.types.LocalDateDto
import java.time.LocalDate
import org.apache.commons.codec.binary.Base64
import org.springframework.stereotype.Component

@Component
class DoctorDtoConverter(val dateConverter: InfallibleConverter<LocalDate, LocalDateDto>) : InfallibleConverter<Doctor, DoctorDto> {
    override fun convert(source: Doctor): DoctorDto {
        val value = requireNotNull(source.id) { "Doctor id cannot be null" }
        return DoctorDto(
            id = DoctorDto.DoctorDtoId(value),
            firstName = source.nameInfo.firstName,
            middleName = source.nameInfo.middleName,
            lastName = source.nameInfo.lastName,
            thumbnail = source.thumbnail?.let { Base64.encodeBase64String(it) },
            title = source.title,
            dateOfBirth = source.dateOfBirth?.let { dateConverter.convert(it) },
        )
    }
}

