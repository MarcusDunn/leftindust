package com.leftindust.mockingbird.doctor

import com.google.firebase.auth.FirebaseAuth
import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.ConversionError.Companion.ConversionFailure
import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.address.toCreateAddress
import com.leftindust.mockingbird.clinic.ClinicDto
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.email.toCreateEmail
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.phone.CreatePhone
import com.leftindust.mockingbird.phone.toCreatePhone
import com.leftindust.mockingbird.user.MediqUserUniqueIdToProofOfValidUserConverter
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.onFailure
import java.time.LocalDate

fun CreateDoctorDto.toCreateDoctor(): Result4k<CreateDoctor, ConversionError<CreateDoctorDto, CreateDoctor>> {

    val userValidator = MediqUserUniqueIdToProofOfValidUserConverter(FirebaseAuth.getInstance())

    return Success(
        CreateDoctorImpl(
            user = when (user.discriminant) {
                CreateDoctorUserDto.CreateDoctorUserDtoType.NoUser -> CreateDoctor.User.NoUser(
                    nameInfo = user.nameInfo
                        ?: return ConversionFailure(Exception("Invalid Input $user"))
                )

                CreateDoctorUserDto.CreateDoctorUserDtoType.Find -> CreateDoctor.User.Find(
                    userUid = user.userUid?.value ?: return ConversionFailure(Exception("Invalid Input $user"))
                )

                CreateDoctorUserDto.CreateDoctorUserDtoType.Create -> CreateDoctor.User.Create(
                    uid = user.userUid ?: return ConversionFailure(Exception("Invalid Input $user")),
                    nameInfo = user.nameInfo
                        ?: return ConversionFailure(Exception("Invalid Input")),
                    group = user.group ?: return ConversionFailure(Exception("Invalid Input $user")),
                    proofOfValidUser = userValidator.convert(user.userUid)
                        ?: return ConversionFailure(Exception("Invalid Input $user"))
                )
            },
            phones = phones.map { it.toCreatePhone().onFailure { e -> return ConversionFailure(e.reason) } },
            title = title,
            clinic = clinic,
            dateOfBirth = dateOfBirth,
            addresses = addresses.map { it.toCreateAddress() },
            emails = emails.map {
                it.toCreateEmail().onFailure { e -> return ConversionFailure(e.reason) }
            },
            patients = patients,
        )
    )

}

data class CreateDoctorImpl(
    override val user: CreateDoctor.User,
    override val phones: List<CreatePhone>,
    override val title: String?,
    override val clinic: List<ClinicDto.ClinicDtoId>,
    override val dateOfBirth: LocalDate?,
    override val addresses: List<CreateAddress>,
    override val emails: List<CreateEmail>,
    override val patients: List<PatientDto.PatientDtoId>,
) : CreateDoctor