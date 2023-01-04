package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success


fun Doctor.toDoctorDto(): Result4k<DoctorDto, ConversionError<Doctor, DoctorDto>> {
    return Success(
        DoctorDto(
            id = DoctorDto.DoctorDtoId(id),
            thumbnail = thumbnail,
            title = title,
            dateOfBirth = dateOfBirth,
        )
    )
}

