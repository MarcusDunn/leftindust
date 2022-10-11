package com.leftindust.mockingbird.patient


import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.contact.CreateContact
import com.leftindust.mockingbird.contact.CreateContactDto
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.email.CreateEmailDto
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.Sex
import com.leftindust.mockingbird.person.UpdateNameInfo
import com.leftindust.mockingbird.phone.CreatePhone
import org.springframework.stereotype.Component
import java.time.LocalDate


@Component
class UpdatePatientDtoToUpdatePatientConverter(

    private val createEmailDtoToCreateEmailConverter: FallibleConverter<CreateEmailDto, CreateEmail>,
    private val createContactDtoToCreateContactConverter: FallibleConverter<CreateContactDto, CreateContact>,

) : FallibleConverter<UpdatePatientDto, UpdatePatient>{

    override fun convert(source: UpdatePatientDto): UpdatePatient {
        return UpdatePatientImpl(
            pid = source.pid,
            nameInfo = Updatable.Update(source.nameInfo),
            phones = Updatable.Update(source.phones),
            addresses = Updatable.Update(source.addresses),
            emails = Updatable.Update(source.emails.map { createEmailDtoToCreateEmailConverter.convert(it)!!}),
            dateOfBirth = Updatable.Update(source.dateOfBirth),
            insuranceNumber = Updatable.Update(source.insuranceNumber),
            sex = Updatable.Update(source.sex),
            gender = Updatable.Update(source.gender),
            ethnicity = Updatable.Update(source.ethnicity),
            emergencyContacts = Updatable.Update(source.emergencyContacts.map { createContactDtoToCreateContactConverter.convert(it)!!}),
            doctors = Updatable.Update(source.doctors),
            thumbnail = Updatable.Update(source.thumbnail)
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
        override val thumbnail: Updatable<String>
    ) : UpdatePatient

}