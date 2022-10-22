package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.ConversionError.Companion.ConversionFailure
import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.contact.CreateContact
import com.leftindust.mockingbird.contact.CreateContactDto
import com.leftindust.mockingbird.contact.toCreateContact
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.email.CreateEmailDto
import com.leftindust.mockingbird.email.toCreateEmail
import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.graphql.types.input.DeletableMapDelegate
import com.leftindust.mockingbird.graphql.types.input.UpdatableMapDelegate
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.MapDelegatingUpdateNameInfoDto
import com.leftindust.mockingbird.person.Sex
import com.leftindust.mockingbird.person.UpdateNameInfo
import com.leftindust.mockingbird.person.UpdateNameInfoDto
import com.leftindust.mockingbird.phone.CreatePhone
import com.leftindust.mockingbird.phone.CreatePhoneDto
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.onFailure
import java.time.LocalDate
import java.util.UUID

class MapDelegatingUpdatePatientDto(val map: Map<String, Any?>) : UpdatePatientDto {
    override val pid: PatientDto.PatientDtoId = runCatching {
        PatientDto.PatientDtoId((map["pid"]!! as Map<*, *>)["value"]!! as UUID)
    }.getOrElse {
        throw IllegalArgumentException("pid is required", it)
    }
    override val nameInfo: Updatable<MapDelegatingUpdateNameInfoDto> by UpdatableMapDelegate(map)
    override val phones: Updatable<List<CreatePhoneDto>> by UpdatableMapDelegate(map)
    override val dateOfBirth: Updatable<LocalDate> by UpdatableMapDelegate(map)
    override val addresses: Updatable<List<CreateAddressDto>> by UpdatableMapDelegate(map)
    override val emails: Updatable<List<CreateEmailDto>> by UpdatableMapDelegate(map)
    override val insuranceNumber: Deletable<String> by DeletableMapDelegate(map)
    override val sex: Updatable<Sex> by UpdatableMapDelegate(map)
    override val gender: Deletable<String> by DeletableMapDelegate(map)
    override val ethnicity: Deletable<Ethnicity> by DeletableMapDelegate(map)
    override val emergencyContacts: Updatable<List<CreateContactDto>> by UpdatableMapDelegate(map)
    override val doctors: Updatable<List<DoctorDto.DoctorDtoId>> by UpdatableMapDelegate(map)
    override val thumbnail: Deletable<String> by DeletableMapDelegate(map)
}

interface UpdatePatientDto{
    val pid: PatientDto.PatientDtoId
    val nameInfo: Updatable<UpdateNameInfoDto>
    val phones: Updatable<List<CreatePhoneDto>>
    val dateOfBirth: Updatable<LocalDate>
    val addresses: Updatable<List<CreateAddressDto>>
    val emails: Updatable<List<CreateEmailDto>>
    val insuranceNumber: Deletable<String>
    val sex: Updatable<Sex>
    val gender: Deletable<String>
    val ethnicity: Deletable<Ethnicity>
    val emergencyContacts: Updatable<List<CreateContactDto>>
    val doctors: Updatable<List<DoctorDto.DoctorDtoId>>
    val thumbnail: Deletable<String>
}

fun UpdatePatientDto.toUpdatePatient(): Result4k<UpdatePatient, ConversionError<UpdatePatientDto, UpdatePatient>> {
    return Success(
        UpdatePatientImpl(
            pid = pid,
            nameInfo = nameInfo,
            phones = phones,
            dateOfBirth = dateOfBirth,
            addresses = addresses,
            emails = emails.map { it.map { it.toCreateEmail().onFailure { return ConversionFailure(it.reason) } } },
            insuranceNumber = insuranceNumber,
            sex = sex,
            gender = gender,
            ethnicity = ethnicity,
            emergencyContacts = emergencyContacts.map { it.map { it.toCreateContact().onFailure { return ConversionFailure(it.reason) } } },
            doctors = doctors,
            thumbnail = thumbnail,
        )
    )
}

private data class UpdatePatientImpl(
    override val pid: PatientDto.PatientDtoId,
    override val nameInfo: Updatable<UpdateNameInfo>,
    override val phones: Updatable<List<CreatePhone>>,
    override val dateOfBirth: Updatable<LocalDate>,
    override val addresses: Updatable<List<CreateAddress>>,
    override val emails: Updatable<List<CreateEmail>>,
    override val insuranceNumber: Deletable<String>,
    override val sex: Updatable<Sex>,
    override val gender: Deletable<String>,
    override val ethnicity: Deletable<Ethnicity>,
    override val emergencyContacts: Updatable<List<CreateContact>>,
    override val doctors: Updatable<List<DoctorDto.DoctorDtoId>>,
    override val thumbnail: Deletable<String>,
) : UpdatePatient


