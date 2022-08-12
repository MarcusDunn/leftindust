package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.address.AddressDto
import com.leftindust.mockingbird.email.Email
import com.leftindust.mockingbird.email.EmailDto
import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.phone.PhoneDto
import org.springframework.stereotype.Component

@Component
class DoctorToDoctorDtoConverter(
    private val phoneToPhoneDtoConverter : InfallibleConverter<Phone, PhoneDto>,
    private val emailToEmailDtoConverter:  InfallibleConverter<Email, EmailDto>,
    private val addressToAddressDtoConverter: InfallibleConverter<Address, AddressDto>
) : InfallibleConverter<Doctor, DoctorDto> {
    override fun convert(source: Doctor): DoctorDto {
        return DoctorDto(
            id = DoctorDto.DoctorDtoId(source.id ?: throw NullEntityIdInConverterException(source)),
            firstName = source.nameInfo.firstName,
            middleName = source.nameInfo.middleName,
            lastName = source.nameInfo.lastName,
            phoneNumbers = source.phones.map { phoneToPhoneDtoConverter.convert(it) }.toList(),
            addresses = source.addresses.map { addressToAddressDtoConverter.convert(it) }.toList(),
            emails = source.emails.map { emailToEmailDtoConverter.convert(it) }.toList(),
            thumbnail = source.thumbnail,
            title = source.title,
            dateOfBirth = source.dateOfBirth,
        )
    }
}

