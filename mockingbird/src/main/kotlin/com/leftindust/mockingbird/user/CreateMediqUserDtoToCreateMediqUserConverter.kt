package com.leftindust.mockingbird.user

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.person.CreateNameInfo
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.onFailure


//class CreateMediqUserDtoToCreateMediqUserConverter() :

fun CreateMediqUserDto.toCreateMediqUser(): Result4k<CreateMediqUser, ConversionError<CreateMediqUserDto, CreateMediqUser>> {
    return Success(
        CreateMediqUserImpl(
            uid = uid,
            nameInfo = nameInfo,
            group = group,
            doctor = doctor,
            proofOfValidUser = uid.toProofOfValidUser(FirebaseAuth.getInstance()).onFailure { throw it.reason.toMockingbirdException() }
        )
    )
}


private data class CreateMediqUserImpl(
    override val uid: MediqUserDto.MediqUserUniqueId,
    override val nameInfo: CreateNameInfo,
    override val group: MediqGroupDto.MediqGroupId?,
    override val doctor: DoctorDto.DoctorDtoId?,
    override val proofOfValidUser: ProofOfValidUser,
) : CreateMediqUser
