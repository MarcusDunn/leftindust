package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.user.MediqUser
import com.leftindust.mockingbird.user.MediqUserDto
import com.leftindust.mockingbird.user.ReadUserService
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class DoctorUserQueryController(
    private val userService: ReadUserService,
    private val mediqUserToMediqUserDtoConverter: InfallibleConverter<MediqUser, MediqUserDto>,
) {
    @SchemaMapping
    suspend fun user(doctorDto: DoctorDto): MediqUserDto? {
        return userService.findByDoctorId(doctorDto.id)?.let { mediqUserToMediqUserDtoConverter.convert(it) }
    }
}