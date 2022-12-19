package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.ConversionError.Companion.ConversionFailure
import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.address.toCreateAddress
import com.leftindust.mockingbird.contact.CreateContact
import com.leftindust.mockingbird.contact.CreateContactDto
import com.leftindust.mockingbird.contact.toCreateContact
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.email.CreateEmailDto
import com.leftindust.mockingbird.email.EmailType
import com.leftindust.mockingbird.email.toCreateEmail
import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.person.*
import com.leftindust.mockingbird.phone.CreatePhone
import com.leftindust.mockingbird.phone.CreatePhoneDto
import com.leftindust.mockingbird.phone.PhoneType
import com.leftindust.mockingbird.phone.toCreatePhone
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.onFailure
import java.time.LocalDate

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
            phones = phones.map {
                it.map { phone ->
                    phone.toCreatePhone().onFailure { e -> return ConversionFailure(e.reason) }
                }
            }
        )
    )
}

data class UpdatePatientImpl(
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

