package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.ConversionError.Companion.ConversionFailure
import com.leftindust.mockingbird.address.AddressType
import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.contact.CreateContact
import com.leftindust.mockingbird.contact.CreateContactDto
import com.leftindust.mockingbird.contact.toCreateContact
import com.leftindust.mockingbird.country.Countries
import com.leftindust.mockingbird.country.CountryState
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.email.CreateEmailDto
import com.leftindust.mockingbird.email.toCreateEmail
import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
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
import kotlin.reflect.full.memberProperties

class MapDelegatingUpdatePatientDto(val map: Map<String, Any?>, patient: UpdatePatientDto) : UpdatePatient {
    override val pid: PatientDto.PatientDtoId = runCatching {
        PatientDto.PatientDtoId((map["pid"]!! as Map<*, *>)["value"]!! as UUID)
    }.getOrElse {
        throw IllegalArgumentException("pid is required", it)
    }
    override val nameInfo: Updatable<MapDelegatingUpdateNameInfoDto> by UpdatableMapDelegate(map)
    override val phones: Updatable<List<CreatePhoneDto>> by UpdatableMapDelegate(map)
    override val dateOfBirth: Updatable<LocalDate> by UpdatableMapDelegate( map)
    override val addresses: Updatable<List<CreateAddressDto>> by UpdatableMapDelegate(map)
    override val emails: Updatable<List<CreateEmail>> by UpdatableMapDelegate(map)
    override val insuranceNumber: Deletable<String> by DeletableMapDelegate(map)
    override val sex: Updatable<Sex> by UpdatableMapDelegate(map)
    override val gender: Deletable<String> by DeletableMapDelegate(map)
    override val ethnicity: Deletable<Ethnicity> by DeletableMapDelegate(map)
    override val emergencyContacts: Updatable<List<CreateContact>> by UpdatableMapDelegate(map)
    override val doctors: Updatable<List<DoctorDto.DoctorDtoId>> by UpdatableMapDelegate(map)
    override val thumbnail: Deletable<String> by DeletableMapDelegate(map)
}

interface Update {}

data class UpdatePatientDto(
    override val id: PatientDtoId,
    val nameInfo: UpdateNameInfoDto,
    val phones: List<CreatePhoneDto>,
    val dateOfBirth: LocalDate,
    val addresses: List<CreateAddressDto>,
    val emails: List<CreateEmailDto>,
    val insuranceNumber: Deletable<String>,
    val sex: Sex,
    val gender: String,
    val ethnicity: Ethnicity,
    val emergencyContacts: List<CreateContactDto>,
    val doctors: List<DoctorDto.DoctorDtoId>,
    val thumbnail: String
) : AbstractGraphQLDto<UpdatePatientDto.PatientDtoId>(), Update {

    companion object {
        const val GRAPHQL_TYPE = "EditPatient"
    }
    data class PatientDtoId(override val value: UUID) : GraphQLID<UUID>
}

inline fun <reified T : Any> T.asMap() : Map<String, Any?> {
    val props = T::class.memberProperties.associateBy { it.name }
    return props.keys.associateWith { props[it]?.get(this) }
}

//fun UpdatePatientDto.toUpdatePatient(): Result4k<UpdatePatient, ConversionError<UpdatePatientDto, UpdatePatient>> {
//    return Success(
//        UpdatePatientImpl(
//            pid = id,
//            nameInfo = nameInfo,
//            phones = phones,
//            dateOfBirth = dateOfBirth,
//            addresses = addresses,
//            emails = emails.map { it.map { it.toCreateEmail().onFailure { return ConversionFailure(it.reason) } } },
//            insuranceNumber = insuranceNumber,
//            sex = sex,
//            gender = gender,
//            ethnicity = ethnicity,
//            emergencyContacts = emergencyContacts.map { it.map { it.toCreateContact().onFailure { return ConversionFailure(it.reason) } } },
//            doctors = doctors,
//            thumbnail = thumbnail,
//        )
//    )
//}

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

class MapDelegatingUpdateAddressDto(val map: Map<String, Any?>) : UpdateAddress {
    override val addressType: Deletable<AddressType> by DeletableMapDelegate(map)
    override val address: Updatable<String> by UpdatableMapDelegate(map)
    override val city: Updatable<String> by UpdatableMapDelegate(map)
    override val country: Updatable<Countries> by UpdatableMapDelegate(map)
    override val province: Updatable<String> by UpdatableMapDelegate(map)
    override val postalCode: Updatable<String> by UpdatableMapDelegate(map)
}

class UpdateAddressDto(
    override val addressType: Deletable<AddressType>,
    override val address: Updatable<String>,
    override val city: Updatable<String>,
    override val country: Updatable<Countries>,
    override val province: Updatable<String>,
    override val postalCode: Updatable<String>
) : UpdateAddress {
    constructor(addressType: String,
                address: String,
                city: String,
                country: String,
                province: String,
                postalCode: String
    ) : this(
            Deletable.Update(AddressType.valueOf(addressType)),
            Updatable.Update(address),
            Updatable.Update(city),
            Updatable.Update(Countries.valueOf(country)),
            Updatable.Update(province),
            Updatable.Update(postalCode)
        )
}

interface UpdateAddress {
    val addressType: Deletable<AddressType>
    val address: Updatable<String>
    val city: Updatable<String>
    val country: Updatable<Countries>
    val province: Updatable<String>
    val postalCode: Updatable<String>
}


