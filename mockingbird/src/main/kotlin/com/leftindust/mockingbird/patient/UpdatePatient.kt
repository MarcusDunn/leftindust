package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.contact.CreateContact
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.Sex
import com.leftindust.mockingbird.person.UpdateNameInfo
import com.leftindust.mockingbird.phone.CreatePhone
import java.time.LocalDate

interface UpdatePatient {
    val pid: PatientDto.PatientDtoId
    val nameInfo: Updatable<UpdateNameInfo>
    val phones: Updatable<List<CreatePhone>>
    val dateOfBirth: Updatable<LocalDate>
    val addresses: Updatable<List<CreateAddress>>
    val emails: Updatable<List<CreateEmail>>
    val insuranceNumber: Deletable<String>
    val sex: Updatable<Sex>
    val gender: Deletable<String>
    val ethnicity: Deletable<Ethnicity>
    val emergencyContacts: Updatable<List<CreateContact>>
    val doctors: Updatable<List<DoctorDto.DoctorDtoId>>
    val thumbnail: Deletable<String>
}
