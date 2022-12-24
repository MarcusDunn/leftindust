package com.leftindust.mockingbird.clinic

fun Clinic.toClinicDto(): ClinicDto {
    return ClinicDto(
        name,
        ClinicDto.ClinicDtoId(id),
    )
}