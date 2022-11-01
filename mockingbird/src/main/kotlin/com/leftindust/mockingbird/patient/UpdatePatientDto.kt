package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.ConversionError.Companion.ConversionFailure
import com.leftindust.mockingbird.address.AddressType
import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.address.toCreateAddress
import com.leftindust.mockingbird.clinic.MapDelegatingCreateAddressDto
import com.leftindust.mockingbird.contact.CreateContact
import com.leftindust.mockingbird.contact.CreateContactDto
import com.leftindust.mockingbird.contact.toCreateContact
import com.leftindust.mockingbird.country.Countries
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.email.CreateEmailDto
import com.leftindust.mockingbird.email.EmailType
import com.leftindust.mockingbird.email.toCreateEmail
import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.graphql.types.input.DeletableMapDelegate
import com.leftindust.mockingbird.graphql.types.input.UpdatableMapDelegate
import com.leftindust.mockingbird.person.CreateNameInfoDto
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.MapDelegatingUpdateNameInfoDto
import com.leftindust.mockingbird.person.Relationship
import com.leftindust.mockingbird.person.Sex
import com.leftindust.mockingbird.person.UpdateNameInfo
import com.leftindust.mockingbird.person.UpdateNameInfoDto
import com.leftindust.mockingbird.phone.CreatePhone
import com.leftindust.mockingbird.phone.CreatePhoneDto
import com.leftindust.mockingbird.phone.PhoneType
import com.leftindust.mockingbird.phone.toCreatePhone
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.onFailure
import java.time.LocalDate
import java.util.UUID

class MapDelegatingUpdatePatientDto(
    val map: Map<String, Any?>,
) : UpdatePatientDto {
    override val pid: PatientDto.PatientDtoId = runCatching {
        PatientDto.PatientDtoId((map["pid"]!! as Map<*, *>)["value"]!! as UUID)
    }.getOrElse {
        throw IllegalArgumentException("pid is required", it)
    }
    override val addresses: Updatable<List<MapDelegatingCreateAddressDto>> by UpdatableMapDelegate(map)
    override val emails: Updatable<List<MapDelegatingCreateEmailDto>> by UpdatableMapDelegate(map)
    override val phones: Updatable<List<MapDelegatingCreatePhoneDto>> by UpdatableMapDelegate(map)
    override val emergencyContacts: Updatable<List<MapDelegatingCreateContactDto>> by UpdatableMapDelegate(map)
    override val nameInfo: Updatable<MapDelegatingUpdateNameInfoDto> by UpdatableMapDelegate(map)
    override val dateOfBirth: Updatable<LocalDate> by UpdatableMapDelegate(map)
    override val insuranceNumber: Deletable<String> by DeletableMapDelegate(map)
    override val sex: Updatable<Sex> by UpdatableMapDelegate(map)
    override val gender: Deletable<String> by DeletableMapDelegate(map)
    override val ethnicity: Deletable<Ethnicity> by DeletableMapDelegate(map)
    override val doctors: Updatable<List<DoctorDto.DoctorDtoId>> by UpdatableMapDelegate(map)
    override val thumbnail: Deletable<String> by DeletableMapDelegate(map)
}

interface UpdatePatientDto {
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
            phones = phones.map { it.map { phone -> phone.toCreatePhone().onFailure { e -> return ConversionFailure(e.reason) } }},
            dateOfBirth = dateOfBirth,
            addresses = addresses.map { it.map { address -> address.toCreateAddress() } },
            emails = emails.map {
                it.map { email ->
                    email.toCreateEmail().onFailure { e -> return ConversionFailure(e.reason) }
                }
            },
            insuranceNumber = insuranceNumber,
            sex = sex,
            gender = gender,
            ethnicity = ethnicity,
            emergencyContacts = emergencyContacts.map {
                it.map { contact ->
                    contact.toCreateContact().onFailure { e -> return ConversionFailure(e.reason) }
                }
            },
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

class MapDelegatingCreatePhoneDto(val map: Map<String, Any?>) : CreatePhoneDto {
    override val type: PhoneType by map
    override val number: String by map
}

class MapDelegatingCreateEmailDto(val map: Map<String, Any?>) : CreateEmailDto {
    override val type: EmailType by map
    override val email: String by map
}

class MapDelegatingCreateContactDto(val map: Map<String, Any?>) : CreateContactDto {
    override val nameInfo: CreateNameInfoDto by map
    override val relationship: Relationship by map
    override val phones: List<CreatePhoneDto> by map
    override val emails: List<CreateEmailDto> by map
}
