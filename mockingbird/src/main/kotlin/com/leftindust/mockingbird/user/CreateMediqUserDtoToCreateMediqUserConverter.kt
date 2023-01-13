package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.person.CreateNameInfo
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success

fun CreateMediqUserDto.toCreateMediqUser(): Result4k<CreateMediqUser, ConversionError<CreateMediqUserDto, CreateMediqUser>> {
    return Success(
        CreateMediqUserImpl(
            uid = uid,
            nameInfo = nameInfo,
            group = group,
            doctor = doctor,
        )
    )
}


private data class CreateMediqUserImpl(
    override val uid: MediqUserDto.MediqUserUniqueId,
    override val nameInfo: CreateNameInfo,
    override val group: MediqGroupDto.MediqGroupId?,
    override val doctor: DoctorDto.DoctorDtoId?,
) : CreateMediqUser
