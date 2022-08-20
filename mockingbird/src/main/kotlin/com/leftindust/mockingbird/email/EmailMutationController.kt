package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.InconvertibleDtoException
import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.doctor.CreateDoctor
import com.leftindust.mockingbird.phone.CreatePhone
import com.leftindust.mockingbird.phone.CreatePhoneDto
import com.leftindust.mockingbird.phone.CreatePhoneService
import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.phone.PhoneDto
import com.leftindust.mockingbird.phone.PhoneToPhoneDtoConverter
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class EmailMutationController(
    private val createPhoneService: CreatePhoneService,
    private val createPhoneDtoToCreatePhoneConverter: FallibleConverter<CreatePhoneDto, CreatePhone>,
    private val phoneToPhoneDtoConverter: InfallibleConverter<Phone, PhoneDto>
) {


    @MutationMapping()
    suspend fun createEmail(@Argument("createPhone") createPhoneDto: CreatePhoneDto) : PhoneDto {
        val createPhone = createPhoneDtoToCreatePhoneConverter.convert(createPhoneDto)
        val newPhone =  createPhoneService.createPhone(createPhone  ?: throw InconvertibleDtoException<CreatePhone>(createPhoneDto))
        return phoneToPhoneDtoConverter.convert(newPhone)
    }
}