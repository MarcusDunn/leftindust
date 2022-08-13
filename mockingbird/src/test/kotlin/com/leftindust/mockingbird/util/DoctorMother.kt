package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.address.AddressToAddressDtoConverter
import com.leftindust.mockingbird.doctor.ClinicDoctorEntity
import com.leftindust.mockingbird.doctor.Doctor
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.DoctorEventEntity
import com.leftindust.mockingbird.doctor.DoctorPatientEntity
import com.leftindust.mockingbird.doctor.DoctorToDoctorDtoConverter
import com.leftindust.mockingbird.email.EmailToEmailDtoConverter
import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.phone.PhoneToPhoneDtoConverter
import com.leftindust.mockingbird.user.MediqUser
import com.leftindust.mockingbird.util.AddressMother.JennysHouse
import com.leftindust.mockingbird.util.PhoneMother.JennysHomePhone
import com.leftindust.mockingbird.util.PhoneMother.JennysWorkPhone
import java.time.LocalDate
import java.time.Month
import java.util.UUID

object DoctorMother {
    val doctorToDoctorDto = DoctorToDoctorDtoConverter(
        PhoneToPhoneDtoConverter(),
        EmailToEmailDtoConverter(),
        AddressToAddressDtoConverter()
    )

    object Jenny {
        const val firstName = "Jenny"
        const val middleName = "Ellis"
        const val lastName = "White"
        val dateOfBirth = LocalDate.of(1924, Month.APRIL, 17)
        val id = UUID.fromString("77941cfc-7808-4c7a-bbbe-62c6a637d5f8")
        val graphqlId = DoctorDto.DoctorDtoId(id)
        val addresses = mutableSetOf(JennysHouse.entityPersisted)
        val emails = mutableSetOf(EmailMother.jennysEmail)
        val phones = mutableSetOf(JennysHomePhone.entityPersisted, JennysWorkPhone.entityPersisted)
        val user: MediqUser? = null
        val events = mutableSetOf<DoctorEventEntity>()
        val thumbnail: ByteArray? = null
        val title: String? = null
        val clinics = mutableSetOf<ClinicDoctorEntity>()
        val patients = mutableSetOf<DoctorPatientEntity>()
        val entityPersisted = Doctor(
            nameInfo = NameInfo(
                firstName = firstName,
                lastName = lastName,
                middleName = middleName
            ),
            addresses = addresses,
            emails = emails,
            phones = phones,
            user = user,
            events = events,
            thumbnail = thumbnail,
            title = title,
            dateOfBirth = dateOfBirth,
            clinics = clinics,
            patients = patients,
        ).apply { id = this@Jenny.id }

        val dto = doctorToDoctorDto.convert(entityPersisted)
    }

}