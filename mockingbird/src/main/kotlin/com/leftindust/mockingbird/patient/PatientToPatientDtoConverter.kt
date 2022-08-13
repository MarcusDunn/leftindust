package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.address.AddressDto
import com.leftindust.mockingbird.address.AddressToAddressDtoConverter
import com.leftindust.mockingbird.email.Email
import com.leftindust.mockingbird.email.EmailDto
import com.leftindust.mockingbird.email.EmailToEmailDtoConverter
import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.phone.PhoneDto
import com.leftindust.mockingbird.phone.PhoneToPhoneDtoConverter
import com.leftindust.mockingbird.phone.Phone_
import org.springframework.stereotype.Component

@Component
class PatientToPatientDtoConverter(
    private val phoneToPhoneDtoConverter: InfallibleConverter<Phone, PhoneDto>,
    private val emailToEmailDtoConverter: InfallibleConverter<Email, EmailDto>,
    private val addressToAddressDtoConverter: InfallibleConverter<Address, AddressDto>
) : InfallibleConverter<Patient, PatientDto> {
    override fun convert(source: Patient): PatientDto {
        return PatientDto(
            id = PatientDto.PatientDtoId(source.id!!),
            firstName = source.nameInfo.firstName,
            middleName = source.nameInfo.middleName,
            lastName = source.nameInfo.lastName,
            addresses = source.addresses.map { addressToAddressDtoConverter.convert(it) }.toList(),
            emails = source.emails.map { emailToEmailDtoConverter.convert(it) }.toList(),
            phoneNumbers = source.phones.map { phoneToPhoneDtoConverter.convert(it) }.toList(),
            thumbnail = source.thumbnail,
            dateOfBirth = source.dateOfBirth,
            insuranceNumber = source.insuranceNumber,
            sex = source.sex,
            gender = source.gender,
            ethnicity = source.ethnicity,
        )
    }
}