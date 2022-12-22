package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.ConversionError.Companion.ConversionFailure
import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.address.CreateAddressGraphQlDto
import com.leftindust.mockingbird.address.toCreateAddress
import com.leftindust.mockingbird.clinic.ClinicDto
import com.leftindust.mockingbird.clinic.MapDelegatingCreateAddressDto
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.email.CreateEmailDto
import com.leftindust.mockingbird.email.CreateEmailGraphQlDto
import com.leftindust.mockingbird.email.toCreateEmail
import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.graphql.types.input.DeletableMapDelegate
import com.leftindust.mockingbird.graphql.types.input.UpdatableMapDelegate
import com.leftindust.mockingbird.patient.*
import com.leftindust.mockingbird.person.CreateNameInfoDto
import com.leftindust.mockingbird.person.MapDelegatingUpdateNameInfoDto
import com.leftindust.mockingbird.person.UpdateNameInfo
import com.leftindust.mockingbird.person.UpdateNameInfoDto
import com.leftindust.mockingbird.phone.CreatePhone
import com.leftindust.mockingbird.phone.CreatePhoneDto
import com.leftindust.mockingbird.phone.CreatePhoneGraphQlDto
import com.leftindust.mockingbird.phone.toCreatePhone
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.onFailure
import java.time.LocalDate
import java.util.*

interface UpdateDoctorDto {
    val did: DoctorDto.DoctorDtoId
    val userUid: Deletable<String>
    val nameInfo: Updatable<UpdateNameInfoDto>
    val phones: Updatable<List<CreatePhoneDto>>
    val title: Deletable<String>
    val clinics: Updatable<List<ClinicDto.ClinicDtoId>>
    val dateOfBirth: Deletable<LocalDate>
    val addresses: Updatable<List<CreateAddressDto>>
    val emails: Updatable<List<CreateEmailDto>>
    val patients: Updatable<List<PatientDto.PatientDtoId>>
}

data class UpdateDoctorGraphQlDto(
    val did: DoctorDto.DoctorDtoId,
    val userUid: String?,
    val nameInfo: CreateNameInfoDto,
    val phones: List<CreatePhoneGraphQlDto>,
    val title: String,
    val clinics: List<ClinicDto.ClinicDtoId>,
    val dateOfBirth: LocalDate,
    val addresses: List<CreateAddressGraphQlDto>,
    val emails: List<CreateEmailGraphQlDto>,
    val patients: List<PatientDto.PatientDtoId>,
)

class MapDelegatingUpdateDoctorDto(
    val map: Map<String, Any?>
) : UpdateDoctorDto {
    override val did: DoctorDto.DoctorDtoId = runCatching {
        DoctorDto.DoctorDtoId((map["did"]!! as Map<*, *>)["value"]!! as UUID)
    }.getOrElse {
        throw IllegalArgumentException("did is required", it)
    }
    override val userUid: Deletable<String> by DeletableMapDelegate(map)
    override val nameInfo: Updatable<MapDelegatingUpdateNameInfoDto> by UpdatableMapDelegate(map)
    override val phones: Updatable<List<MapDelegatingCreatePhoneDto>> by UpdatableMapDelegate(map)
    override val title: Deletable<String> by DeletableMapDelegate(map)
    override val clinics: Updatable<List<ClinicDto.ClinicDtoId>> by UpdatableMapDelegate(map)
    override val dateOfBirth: Deletable<LocalDate> by DeletableMapDelegate(map)
    override val addresses: Updatable<List<MapDelegatingCreateAddressDto>> by UpdatableMapDelegate(map)
    override val emails: Updatable<List<MapDelegatingCreateEmailDto>> by UpdatableMapDelegate(map)
    override val patients: Updatable<List<PatientDto.PatientDtoId>> by UpdatableMapDelegate(map)
}

fun UpdateDoctorDto.toUpdateDoctor(): Result4k<UpdateDoctor, ConversionError<UpdateDoctorDto, UpdateDoctor>> {
    return Success(
        UpdateDoctorImpl(
            did,
            userUid,
            nameInfo,
            phones.map { it.map { phone -> phone.toCreatePhone().onFailure { e -> return ConversionFailure(e.reason) } } },
            title,
            clinics,
            dateOfBirth,
            addresses.map { it.map { address -> address.toCreateAddress() } },
            emails.map { it.map { email -> email.toCreateEmail().onFailure { e -> return ConversionFailure(e.reason) } } },
            patients
        )
    )
}

data class UpdateDoctorImpl(
    override val did: DoctorDto.DoctorDtoId,
    override val userUid: Deletable<String>,
    override val nameInfo: Updatable<UpdateNameInfo>,
    override val phones: Updatable<List<CreatePhone>>,
    override val title: Deletable<String>,
    override val clinics: Updatable<List<ClinicDto.ClinicDtoId>>,
    override val dateOfBirth: Deletable<LocalDate>,
    override val addresses: Updatable<List<CreateAddress>>,
    override val emails: Updatable<List<CreateEmail>>,
    override val patients: Updatable<List<PatientDto.PatientDtoId>>
) : UpdateDoctor