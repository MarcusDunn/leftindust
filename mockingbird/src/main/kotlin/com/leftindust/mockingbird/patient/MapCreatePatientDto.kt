package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.contact.CreateContactDto
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.email.CreateEmailDto
import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.graphql.types.toDeletable
import com.leftindust.mockingbird.graphql.types.toUpdatable
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.Sex
import com.leftindust.mockingbird.person.UpdateNameInfoDto
import com.leftindust.mockingbird.phone.CreatePhoneDto
import org.springframework.graphql.data.ArgumentValue
import java.time.LocalDate

data class ArgumentValueUpdatePatientDto(
    val pid: PatientDto.PatientDtoId,
    val nameInfo: ArgumentValue<UpdateNameInfoDto> = ArgumentValue.omitted(),
    val phones: ArgumentValue<List<CreatePhoneDto>> = ArgumentValue.omitted(),
    val dateOfBirth: ArgumentValue<LocalDate> = ArgumentValue.omitted(),
    val addresses: ArgumentValue<List<CreateAddressDto>> = ArgumentValue.omitted(),
    val emails: ArgumentValue<List<CreateEmailDto>> = ArgumentValue.omitted(),
    val insuranceNumber: ArgumentValue<String> = ArgumentValue.omitted(),
    val sex: ArgumentValue<Sex> = ArgumentValue.omitted(),
    val gender: ArgumentValue<String> = ArgumentValue.omitted(),
    val ethnicity: ArgumentValue<Ethnicity> = ArgumentValue.omitted(),
    val emergencyContacts: ArgumentValue<List<CreateContactDto>> = ArgumentValue.omitted(),
    val doctors: ArgumentValue<List<DoctorDto.DoctorDtoId>> = ArgumentValue.omitted(),
    val thumbnail: ArgumentValue<String> = ArgumentValue.omitted(),
)


fun ArgumentValueUpdatePatientDto.toUpdatePatientDto(): UpdatePatientDto {
    return UpdatePatientDtoImpl(
        pid = pid,
        nameInfo = nameInfo.toUpdatable(),
        phones = phones.toUpdatable(),
        dateOfBirth = dateOfBirth.toUpdatable(),
        addresses = addresses.toUpdatable(),
        emails = emails.toUpdatable(),
        insuranceNumber = insuranceNumber.toDeletable(),
        sex = sex.toUpdatable(),
        gender = gender.toDeletable(),
        ethnicity = ethnicity.toDeletable(),
        emergencyContacts = emergencyContacts.toUpdatable(),
        doctors = doctors.toUpdatable(),
        thumbnail = thumbnail.toDeletable(),
    )
}

data class UpdatePatientDtoImpl(
    override val pid: PatientDto.PatientDtoId,
    override val nameInfo: Updatable<UpdateNameInfoDto>,
    override val phones: Updatable<List<CreatePhoneDto>>,
    override val dateOfBirth: Updatable<LocalDate>,
    override val addresses: Updatable<List<CreateAddressDto>>,
    override val emails: Updatable<List<CreateEmailDto>>,
    override val insuranceNumber: Deletable<String>,
    override val sex: Updatable<Sex>,
    override val gender: Deletable<String>,
    override val ethnicity: Deletable<Ethnicity>,
    override val emergencyContacts: Updatable<List<CreateContactDto>>,
    override val doctors: Updatable<List<DoctorDto.DoctorDtoId>>,
    override val thumbnail: Deletable<String>,
) : UpdatePatientDto