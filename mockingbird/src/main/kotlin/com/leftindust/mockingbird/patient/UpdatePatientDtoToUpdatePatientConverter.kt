package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.ConversionError.Companion.ConversionFailure
import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.contact.CreateContact
import com.leftindust.mockingbird.contact.toCreateContact
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.email.toCreateEmail
import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.Sex
import com.leftindust.mockingbird.person.UpdateNameInfo
import com.leftindust.mockingbird.person.toUpdateNameInfo
import com.leftindust.mockingbird.phone.CreatePhone
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.onFailure
import java.time.LocalDate


fun UpdatePatientDto.toUpdatePatient(): Result4k<UpdatePatient, ConversionError<UpdatePatientDto, UpdatePatient>> {
    return Success(
        UpdatePatientImpl(
            pid = pid,
            nameInfo = Updatable.Update(nameInfo.toUpdateNameInfo()),
            phones = Updatable.Update(phones),
            addresses = Updatable.Update(addresses),
            emails = Updatable.Update(emails.map {
                it.toCreateEmail() ?: return ConversionFailure(
                    Exception("Invalid Email type")
                )
            }),
            dateOfBirth = Updatable.Update(dateOfBirth),
            insuranceNumber = Updatable.Update(insuranceNumber),
            sex = Updatable.Update(sex),
            gender = Updatable.Update(gender),
            ethnicity = Updatable.Update(ethnicity),
            emergencyContacts = Updatable.Update(emergencyContacts.map {
                it.toCreateContact()
                    .onFailure { e -> return ConversionFailure(e.reason) }
            }),
            doctors = Updatable.Update(doctors),
            thumbnail = thumbnail?.let { Updatable.Update(it) } ?: Deletable.Delete()
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
    override val insuranceNumber: Updatable<String>,
    override val sex: Updatable<Sex>,
    override val gender: Updatable<String>,
    override val ethnicity: Updatable<Ethnicity>,
    override val emergencyContacts: Updatable<List<CreateContact>>,
    override val doctors: Updatable<List<DoctorDto.DoctorDtoId>>,
    override val thumbnail: Deletable<String>
) : UpdatePatient