package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.contact.CreateContactDto
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.Sex
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.email.CreateEmailDto
import com.leftindust.mockingbird.graphql.types.LocalDateDto
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.person.UpdateNameInfoDto
import com.leftindust.mockingbird.phone.CreatePhoneDto

data class UpdatePatientDto(
    override val pid: PatientDto.PatientDtoId,
    override val nameInfo: Updatable<UpdateNameInfoDto>,
    override val phones: Updatable<List<CreatePhoneDto>>,
    override val dateOfBirth: Updatable<LocalDateDto>,
    override val addresses: Updatable<List<CreateAddressDto>>,
    override val emails: Updatable<List<CreateEmailDto>>,
    override val insuranceNumber: Updatable<String>,
    override val sex: Updatable<Sex>,
    override val gender: Updatable<String>,
    override val ethnicity: Updatable<Ethnicity>,
    override val emergencyContacts: Updatable<List<CreateContactDto>>,
    override val doctors: Updatable<List<DoctorDto.DoctorDtoId>>,
    override val thumbnail: Updatable<String>,
) : UpdatePatient

